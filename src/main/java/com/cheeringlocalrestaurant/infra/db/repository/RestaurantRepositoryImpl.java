package com.cheeringlocalrestaurant.infra.db.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cheeringlocalrestaurant.domain.model.restaurant.Restaurant;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccountNotFoundException;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserRole;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QResto;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoAccount;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QUser;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.Resto;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoAccount;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoName;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoAccountRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoHistoryRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoNameRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestroRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.UserRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.util.JpaRepositoryProxy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.SQLTemplates;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SQLTemplates sqlTemplates;

    @Autowired
    private RestroRepository restroRepository;
    @Autowired
    private RestoHistoryRepository restoHistoryRepository;
    @Autowired
    private RestoNameRepository restoNameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestoAccountRepository restoAccountRepository;

    @Override
    public RestaurantAccount getAccountById(RestaurantId restaurantId) throws RestaurantAccountNotFoundException {

        QResto qResto = QResto.resto;
        RestaurantAccount restaurantAccount = findAccount(qResto.restaurantId.eq(restaurantId.getValue()));
        if (null == restaurantAccount) {
            throw new RestaurantAccountNotFoundException();
        }

        return restaurantAccount;
    }
    
    @Override
    public Optional<RestaurantAccount> findAccountByMailAddress(MailAddress mailAddress) {

        QUser qUser = QUser.user;
        RestaurantAccount restaurantAccount = findAccount(qUser.mailAddress.eq(mailAddress.getValue()));

        return Optional.ofNullable(restaurantAccount);
    }
    
    private RestaurantAccount findAccount(BooleanExpression expression) {

        QUser qUser = QUser.user;
        QRestoAccount qRestoAccount = QRestoAccount.restoAccount;
        QResto qResto = QResto.resto;
        JPASQLQuery<?> query = new JPASQLQuery<Void>(entityManager, sqlTemplates);
        // @formatter:off
        RestaurantAccount restaurantAccount 
                = query.select(Projections.constructor(RestaurantAccount.class, 
                        qUser.userId, qResto.restaurantId, qUser.mailAddress))
                        .from(qUser)
                            .innerJoin(qRestoAccount)
                                .on(qUser.userId.eq(qRestoAccount.userId))
                            .innerJoin(qResto)
                                .on(qRestoAccount.restaurantId.eq(qResto.restaurantId))
                        .where(expression
                                .and(qUser.userRole.eq(UserRole.RESTAURANT_ADMIN.getValue()))
                        ).fetchOne();
        // @formatter:on

        return restaurantAccount;
    }

    @Override
    public Restaurant findById(RestaurantId restaurantId) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public RestaurantId save(RestaurantTempRegister tempRegister, RemoteIpAddress remoteIpAddress) {

        // 飲食店
        Resto resto = new Resto();
        resto = JpaRepositoryProxy.save(restroRepository, resto, remoteIpAddress);
        Long restoId = resto.getRestaurantId();

        // 飲食店履歴
        RestoHistory restoHistory = new RestoHistory();
        restoHistory.setRestaurantId(restoId);
        restoHistory = JpaRepositoryProxy.save(restoHistoryRepository, restoHistory, remoteIpAddress);
        Long restoRevId = restoHistory.getRestaurantHistoryId();

        // 飲食店名
        RestoName restoName = new RestoName();
        restoName.setRestaurantHistoryId(restoRevId);
        restoName.setRestaurantName(tempRegister.getName().getValue());
        JpaRepositoryProxy.save(restoNameRepository, restoName, remoteIpAddress);

        // ユーザー
        com.cheeringlocalrestaurant.infra.db.jpa.entity.User user = new com.cheeringlocalrestaurant.infra.db.jpa.entity.User();
        user.setMailAddress(tempRegister.getMailAddress().getValue());
        user.setUserRole(UserRole.RESTAURANT_ADMIN.getValue());
        user = JpaRepositoryProxy.save(userRepository, user, remoteIpAddress);
        Long userId = user.getUserId();

        // 飲食店アカウント
        RestoAccount restoAccount = new RestoAccount();
        restoAccount.setRestaurantId(restoId);
        restoAccount.setUserId(userId);
        JpaRepositoryProxy.save(restoAccountRepository, restoAccount, remoteIpAddress);

        return new RestaurantId(restoId);
    }
}
