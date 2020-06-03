package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cheeringlocalrestaurant.SQLTemplatesConfig;
import com.cheeringlocalrestaurant.domain.model.LoginAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenId;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenPublishedDateTime;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Slf4j
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Value("${restaurant.login.expiration.hours}")
    int loginExpirationHours;

    private final String name = "いろは食堂";
    private final String email = "iroha@example.com";
    private final String remoteIPAddr = "127.0.0.1";

    private RestaurantTempRegister tempRegister = new RestaurantTempRegister(name, email);

    @Test
    void _仮登録() {

        RestaurantId idActual = restaurantRepository.save(tempRegister, remoteIPAddr);
        assertNotNull(idActual);
        assertNotNull(idActual.getValue());

        RestaurantAccount restaurantAccount = restaurantRepository.findByMailAddress(tempRegister.getMailAddress());

        assertNotNull(restaurantAccount);
        assertNotNull(restaurantAccount.getUserId());
        assertNotNull(restaurantAccount.getRestaurantId());
        assertEquals(email, restaurantAccount.getMailAddress().getValue());
        log.info(restaurantAccount.toString());
    }

    @Test
    void _ログイントークンの登録() {
        final LocalDateTime currentDateTime = LocalDateTime.now();
        final LocalDateTime loginExpirationDateTime = currentDateTime.plusHours(loginExpirationHours);
        final AccessTokenPublishedDateTime loginTokenPublishedDateTime = new AccessTokenPublishedDateTime(currentDateTime);
        
        final AccessTokenId accessTokenId = restaurantRepository.registerAccessToken(tempRegister.getMailAddress(), loginTokenPublishedDateTime);
        assertNotNull(accessTokenId);
        assertNotNull(accessTokenId.getValue());

        final LoginAccount loginAccount = restaurantRepository.getLoginAccount(accessTokenId);
        assertNotNull(loginAccount);
    }
}
