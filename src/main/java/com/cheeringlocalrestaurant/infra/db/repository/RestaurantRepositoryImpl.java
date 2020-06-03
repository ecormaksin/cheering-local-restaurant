package com.cheeringlocalrestaurant.infra.db.repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cheeringlocalrestaurant.domain.model.restaurant.Restaurant;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.UserRole;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.infra.db.jpa.EntityUtil;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QResto;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoAccount;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoName;
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
import com.querydsl.core.types.Projections;
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
    public RestaurantAccount findByMailAddress(String mailAddress) {

        QUser qUser = QUser.user;
        QRestoAccount qRestoAccount = QRestoAccount.restoAccount;
        QResto qResto = QResto.resto;
        QRestoHistory qRestoHistory = QRestoHistory.restoHistory;
        QRestoName qRestoName = QRestoName.restoName;
        JPASQLQuery<?> query = new JPASQLQuery<Void>(entityManager, sqlTemplates);
        Date systemDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        // @formatter:off
        RestaurantAccount restaurantAccount 
                = query.select(Projections.constructor(RestaurantAccount.class, 
                        qUser.userId, qResto.restaurantId, qRestoName.restaurantName, qUser.mailAddress))
                        .from(qUser)
                            .innerJoin(qRestoAccount)
                                .on(qUser.userId.eq(qRestoAccount.userId))
                            .innerJoin(qResto)
                                .on(qRestoAccount.restaurantId.eq(qResto.restaurantId))
                            .innerJoin(qRestoHistory)
                                .on(qResto.restaurantId.eq(qRestoHistory.restaurantId))
                            .innerJoin(qRestoName)
                                .on(qRestoHistory.restaurantHistoryId.eq(qRestoName.restaurantHistoryId))
                        .where(qUser.mailAddress.eq(mailAddress)
                                .and(qUser.userRole.eq(UserRole.RESTAURANT_ADMIN.getValue()))
                                .and(qRestoHistory.startDate.loe(systemDate))
                                .and(qRestoHistory.endDate.goe(systemDate))
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
    public RestaurantAccount findAccountById(RestaurantId restaurantId) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public RestaurantId save(RestaurantTempRegister tempRegister, String remoteIpAddress) {

        // 飲食店
        Resto resto = new Resto();
        EntityUtil.setCommonColumn(resto, remoteIpAddress);
        resto = restroRepository.save(resto);
        Long restoId = resto.getRestaurantId();

        // 飲食店履歴
        RestoHistory restoHistory = new RestoHistory();
        restoHistory.setRestaurantId(restoId);
        EntityUtil.setCommonColumn(restoHistory, remoteIpAddress);
        restoHistory = restoHistoryRepository.save(restoHistory);
        Long restoRevId = restoHistory.getRestaurantHistoryId();

        // 飲食店名
        RestoName restoName = new RestoName();
        restoName.setRestaurantHistoryId(restoRevId);
        restoName.setRestaurantName(tempRegister.getName().getValue());
        restoNameRepository.save(restoName);

        // ユーザー
        com.cheeringlocalrestaurant.infra.db.jpa.entity.User user = new com.cheeringlocalrestaurant.infra.db.jpa.entity.User();
        user.setMailAddress(tempRegister.getMailAddress().getValue());
        user.setUserRole(UserRole.RESTAURANT_ADMIN.getValue());
        EntityUtil.setCommonColumn(user, remoteIpAddress);
        user = userRepository.save(user);
        Long userId = user.getUserId();

        // 飲食店アカウント
        RestoAccount restoAccount = new RestoAccount();
        restoAccount.setRestaurantId(restoId);
        restoAccount.setUserId(userId);
        EntityUtil.setCommonColumn(restoAccount, remoteIpAddress);
        restoAccountRepository.save(restoAccount);

        return new RestaurantId(restoId);
    }

}
