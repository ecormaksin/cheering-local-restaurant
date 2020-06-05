package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.Resto;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoName;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoNameRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestroRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Slf4j
@Transactional
@AutoConfigureTestEntityManager
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    private RestroRepository restroRepository;
    @Autowired
    private RestoNameRepository restoNameRepository;
    
    private JPAQueryFactory queryFactory;
    
    private static final String restoNameStr = TestDataUtils.RESTAURANT_NAME;
    private static final String mailAddressStr = TestDataUtils.MAIL_ADDRESS;
    private static final RemoteIpAddress remoteIpAddress = TestDataUtils.remoteIpAddress;
    private static final RestaurantTempRegister restaurantTempRegister = TestDataUtils.restaurantTempRegister;
    
    @BeforeEach
    void setup() {
        queryFactory = new JPAQueryFactory(entityManager.getEntityManager());
    }

    @Test
    void _仮登録() throws Exception {

        try {

            RestaurantId restaurantId = restaurantRepository.save(restaurantTempRegister, remoteIpAddress);
            assertNotNull(restaurantId);
            
            Long restoIdLng = restaurantId.getValue();
            assertNotNull(restoIdLng);
    
            RestaurantAccount restaurantAccount = restaurantRepository.getAccountById(restaurantId);

            // エンティティ
            assertNotNull(restaurantAccount);
            // ユーザーID
            UserId userId = restaurantAccount.getUserId();
            assertNotNull(userId);
            assertNotNull(userId.getValue());
            // レストランID
            RestaurantId restaurantIdAct = restaurantAccount.getRestaurantId();
            assertNotNull(restaurantIdAct);
            assertEquals(restaurantId, restaurantIdAct);
            assertEquals(restoIdLng, restaurantIdAct.getValue());
            // メールアドレス
            MailAddress mailAddressAct = restaurantAccount.getMailAddress();
            assertNotNull(mailAddressAct);
            String mailAddrAct = mailAddressAct.getValue();
            assertNotNull(mailAddrAct);
            assertEquals(mailAddressStr, mailAddrAct);
            log.info(restaurantAccount.toString());
            

            // ** エンティティの確認
            // 飲食店
            Resto resto = restroRepository.getOne(restoIdLng);
            assertNotNull(resto.getRegisteredTimestamp());
            assertNotNull(resto.getRemoteIpAddress());
            // 飲食店履歴
            QRestoHistory qRestoHistory = QRestoHistory.restoHistory;
            // @formatter:off
            RestoHistory restoHistory = queryFactory.selectFrom(qRestoHistory)
                    .where(qRestoHistory.restaurantId.eq(restoIdLng))
                    .fetchOne();
            // @formatter:on
            assertNotNull(restoHistory);
            Long restaurantHistoryId = restoHistory.getRestaurantHistoryId();
            assertNotNull(restaurantHistoryId);
            assertNotNull(restoHistory.getEndDate());
            assertNotNull(restoHistory.getRegisteredTimestamp());
            assertNotNull(restoHistory.getRemoteIpAddress());
            Long restoHistRestoId = restoHistory.getRestaurantId();
            assertNotNull(restoHistRestoId);
            assertEquals(restoIdLng, restoHistRestoId);
            assertNotNull(restoHistory.getStartDate());
            // 飲食店名
            RestoName restoName = restoNameRepository.getOne(restaurantHistoryId);
            assertNotNull(restoName);
            String restoNameStrAct = restoName.getRestaurantName();
            assertEquals(restoNameStr, restoNameStrAct);

        } catch (Exception e) {
            fail(e);
        }
    }
}
