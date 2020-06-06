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
import com.cheeringlocalrestaurant.domain.type.account.UserRole;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.Resto;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoAccount;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoName;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.User;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoAccountRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestoNameRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestroRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.UserRepository;
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
    @Autowired
    private RestoAccountRepository restoAccountRepository;
    @Autowired
    private UserRepository userRepository;
    
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

            final RestaurantId restaurantId = restaurantRepository.save(restaurantTempRegister, remoteIpAddress);
            assertNotNull(restaurantId);
            
            final Long restoIdLng = restaurantId.getValue();
            assertNotNull(restoIdLng);
    
            final RestaurantAccount restaurantAccount = restaurantRepository.getAccountById(restaurantId);

            // エンティティ
            assertNotNull(restaurantAccount);
            // ユーザーID
            final UserId userId = restaurantAccount.getUserId();
            final Long userIdLng = userId.getValue();
            assertNotNull(userIdLng);
            // レストランID
            final RestaurantId restaurantIdAct = restaurantAccount.getRestaurantId();
            assertEquals(restoIdLng, restaurantIdAct.getValue());
            // メールアドレス
            final MailAddress mailAddressAct = restaurantAccount.getMailAddress();
            final String mailAddrAct = mailAddressAct.getValue();
            assertEquals(mailAddressStr, mailAddrAct);
            log.info(restaurantAccount.toString());
            

            // ** エンティティの確認
            // 飲食店
            final Resto resto = restroRepository.getOne(restoIdLng);
            assertNotNull(resto.getRemoteIpAddress());
            assertNotNull(resto.getRegisteredTimestamp());
            // 飲食店履歴
            final QRestoHistory qRestoHistory = QRestoHistory.restoHistory;
            // @formatter:off
            final RestoHistory restoHistory = queryFactory.selectFrom(qRestoHistory)
                    .where(qRestoHistory.restaurantId.eq(restoIdLng))
                    .fetchOne();
            // @formatter:on
            assertNotNull(restoHistory);
            final Long restaurantHistoryId = restoHistory.getRestaurantHistoryId();
            assertNotNull(restaurantHistoryId);
            final Long restoHistRestoId = restoHistory.getRestaurantId();
            assertNotNull(restoHistRestoId);
            assertEquals(restoIdLng, restoHistRestoId);
            assertNotNull(restoHistory.getStartDate());
            assertNotNull(restoHistory.getEndDate());
            assertNotNull(restoHistory.getRemoteIpAddress());
            assertNotNull(restoHistory.getRegisteredTimestamp());
            // 飲食店名
            final RestoName restoName = restoNameRepository.getOne(restaurantHistoryId);
            assertNotNull(restoName);
            final String restoNameStrAct = restoName.getRestaurantName();
            assertEquals(restoNameStr, restoNameStrAct);
            // 飲食店アカウント
            final RestoAccount restoAccount = restoAccountRepository.getOne(restoIdLng);
            assertNotNull(restoAccount);
            assertEquals(userIdLng, restoAccount.getUserId());
            assertNotNull(restoAccount.getRemoteIpAddress());
            assertNotNull(restoAccount.getRegisteredTimestamp());
            // ユーザー
            final User user = userRepository.getOne(userIdLng);
            assertNotNull(user);
            assertEquals(mailAddressStr, user.getMailAddress());
            assertEquals(UserRole.RESTAURANT_ADMIN.getValue(), user.getUserRole());
            assertNotNull(user.getRemoteIpAddress());
            assertNotNull(user.getRegisteredTimestamp());

        } catch (Exception e) {
            fail(e);
        }
    }
}
