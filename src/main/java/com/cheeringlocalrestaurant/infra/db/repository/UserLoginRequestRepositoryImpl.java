package com.cheeringlocalrestaurant.infra.db.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequest;
import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestNotFoundException;
import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestRepository;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;
import com.cheeringlocalrestaurant.infra.db.JavaTimeDateConverter;
import com.cheeringlocalrestaurant.infra.db.dto.UserLoginRequestDTO;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.LoginRequest;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QLoginRequest;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QUser;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.LoginRequestRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.util.JpaRepositoryProxy;
import com.querydsl.core.types.Projections;
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
        restoLoginRequest.setTokenExpirationDatetime(JavaTimeDateConverter.toTimestampFromLocalDateTime(expirationDateTime.getValue()));
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
                JavaTimeDateConverter.toLocalDateTimeFromTimestamp(loginRequestDTO.getAccessTokenExpirationDateTime())
                );
        // @formatter:on

        return loginRequest;
    }
}
