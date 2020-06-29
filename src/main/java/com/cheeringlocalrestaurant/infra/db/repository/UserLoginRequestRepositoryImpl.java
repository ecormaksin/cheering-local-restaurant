package com.cheeringlocalrestaurant.infra.db.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenExpiredException;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenNotFoundException;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenUpdatedException;
import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequest;
import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestNotFoundException;
import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestRepository;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;
import com.cheeringlocalrestaurant.infra.db.dto.UserLoginRequestDTO;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.LoginRequest;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QLoginRequest;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QUser;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.LoginRequestRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.util.JpaRepositoryProxy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.SQLTemplates;

@Repository
public class UserLoginRequestRepositoryImpl implements UserLoginRequestRepository {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SQLTemplates sqlTemplates;

    @Autowired
    private LoginRequestRepository loginRequestRepository;

    private JPAQueryFactory queryFactory;
    
    @PostConstruct
    void postConstruct() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    // @formatter:off
    public LoginRequestId registerAccessToken(UserId userId, 
            AccessToken accessToken, 
            AccessTokenExpirationDateTime expirationDateTime, 
            RemoteIpAddress remoteIpAddress) {
    // @formatter:on
        
        LoginRequest restoLoginRequest = new LoginRequest();
        restoLoginRequest.setUserId(userId.getValue());
        restoLoginRequest.setAccessToken(accessToken.getValue());
        restoLoginRequest.setTokenExpirationDatetime(Timestamp.valueOf(expirationDateTime.getValue()));
        restoLoginRequest = JpaRepositoryProxy.save(loginRequestRepository, restoLoginRequest, remoteIpAddress);

        return new LoginRequestId(restoLoginRequest.getId());
    }

    @Override
    public UserLoginRequest getLoginRequest(LoginRequestId loginRequestId) throws UserLoginRequestNotFoundException {

        QLoginRequest qLoginRequest = QLoginRequest.loginRequest;
        QUser qUser = QUser.user;
        JPASQLQuery<?> query = new JPASQLQuery<Void>(entityManager, sqlTemplates);
        // @formatter:off
        UserLoginRequestDTO loginRequestDTO
                = query.select(Projections.constructor(UserLoginRequestDTO.class, 
                            qLoginRequest.userId, 
                            qUser.mailAddress, 
                            qLoginRequest.accessToken, 
                            qLoginRequest.tokenExpirationDatetime
                                )
                        ).from(qLoginRequest)
                            .innerJoin(qUser)
                                .on(qLoginRequest.userId.eq(qUser.userId))
                        .where(qLoginRequest.id.eq(loginRequestId.getValue()))
                        .fetchOne();
        // @formatter:on
        if (null == loginRequestDTO) {
            throw new UserLoginRequestNotFoundException();
        }

        // @formatter:off
        UserLoginRequest loginRequest = new UserLoginRequest(
                loginRequestDTO.getUserId(),
                loginRequestDTO.getMailAddress(),
                loginRequestDTO.getAccessToken(),
                loginRequestDTO.getAccessTokenExpirationDateTime().toLocalDateTime()
                );
        // @formatter:on

        return loginRequest;
    }

    @Override
    public UserId findByAccessToken(AccessToken accessToken) {

        QLoginRequest qLoginRequest = QLoginRequest.loginRequest;
        // @formatter:off
        final LoginRequest loginRequest = queryFactory.selectFrom(qLoginRequest)
                .where(qLoginRequest.accessToken.eq(accessToken.getValue()))
                .fetchOne();
        // @formatter:on
        
        // アクセストークンの存在チェック
        if (null == loginRequest) throw new LoginAccessTokenNotFoundException();
        
        // アクセストークンの有効期限切れチェック
        LocalDateTime tokenExpirationDatetime = loginRequest.getTokenExpirationDatetime().toLocalDateTime();
        if (tokenExpirationDatetime.isBefore(LocalDateTime.now())) {
            throw new LoginAccessTokenExpiredException();
        }
        
        // アクセストークンの更新チェック
        final Long userIdLng = loginRequest.getUserId();
        final Timestamp registeredTimestamp = loginRequest.getRegisteredTimestamp();
        // @formatter:off
        final List<LoginRequest> newLoginRequests = queryFactory.selectFrom(qLoginRequest)
                .where(qLoginRequest.userId.eq(userIdLng)
                        .and(qLoginRequest.registeredTimestamp.after(registeredTimestamp))
                ).fetch();
        // @formatter:on
        if (newLoginRequests.size() > 0) {
            throw new LoginAccessTokenUpdatedException();
        }
        
        return new UserId(loginRequest.getUserId());
    }
}
