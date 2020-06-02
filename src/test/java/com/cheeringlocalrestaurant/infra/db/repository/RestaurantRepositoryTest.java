package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
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
    }

}
