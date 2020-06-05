package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Slf4j
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    private static final String mailAddressStr = TestDataUtils.MAIL_ADDRESS;
    private static final RemoteIpAddress remoteIpAddress = TestDataUtils.remoteIpAddress;
    private static final RestaurantTempRegister restaurantTempRegister = TestDataUtils.restaurantTempRegister;
    
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

        } catch (Exception e) {
            fail(e);
        }
    }
}
