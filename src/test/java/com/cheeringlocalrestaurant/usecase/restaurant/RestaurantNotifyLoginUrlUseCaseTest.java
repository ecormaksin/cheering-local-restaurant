package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

import com.cheeringlocalrestaurant.domain.type.account.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenId;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenPublishedDateTime;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RestaurantNotifyLoginUrlUseCaseTest {

    @Autowired
    RestaurantNotifyLoginUrlUseCase restaurantNotifyLoginUrlUseCase;
    
    @Value("${restaurant.login.expiration.hours}")
    int loginExpirationHours;
    
    private final String email = "iroha@example.com";
    private final MailAddress mailAddress = new MailAddress(email);
    private final String remoteIPAddr = "127.0.0.1";

    @Test
    void test() {
        AccessTokenId accessTokenId = restaurantNotifyLoginUrlUseCase.execute(mailAddress, remoteIPAddr);
        assertNotNull(accessTokenId);
    }

}
