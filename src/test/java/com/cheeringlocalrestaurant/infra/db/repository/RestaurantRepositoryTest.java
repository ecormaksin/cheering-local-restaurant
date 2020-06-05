package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Slf4j
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    private final String email = "iroha@example.com";
    private final RemoteIpAddress remoteIpAddr = new RemoteIpAddress("127.0.0.1");

    private RestaurantTempRegister tempRegister = new RestaurantTempRegister("いろは食堂", email);

    @Test
    void _仮登録() throws Exception {

        RestaurantId restaurantId = restaurantRepository.save(tempRegister, remoteIpAddr);
        assertNotNull(restaurantId);
        assertNotNull(restaurantId.getValue());

        RestaurantAccount restaurantAccount = restaurantRepository.getAccountById(restaurantId);

        assertNotNull(restaurantAccount);
        assertNotNull(restaurantAccount.getUserId());
        assertNotNull(restaurantAccount.getRestaurantId());
        assertEquals(email, restaurantAccount.getMailAddress().getValue());
        log.info(restaurantAccount.toString());
    }
}
