package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cheeringlocalrestaurant.SQLTemplatesConfig;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestEntityManager
@Transactional
@Slf4j
@ImportAutoConfiguration(SQLTemplatesConfig.class)
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Test
    void _仮登録() {
        final String name = "いろは食堂";
        final String email = "iroha@example.com";

        RestaurantTempRegister tempRegister = new RestaurantTempRegister(name, email);
        RestaurantId idActual = restaurantRepository.save(tempRegister, "127.0.0.1");
        assertNotNull(idActual);
        assertNotNull(idActual.getValue());

        RestaurantAccount restaurantAccount = restaurantRepository.findByMailAddress(tempRegister.getMailAddress());

        assertNotNull(restaurantAccount);
        assertNotNull(restaurantAccount.getUserId());
        assertNotNull(restaurantAccount.getRestaurantId());
        assertEquals(name, restaurantAccount.getRestaurantName().getValue());
        assertEquals(email, restaurantAccount.getMailAddress().getValue());
        log.info(restaurantAccount.toString());
    }

}
