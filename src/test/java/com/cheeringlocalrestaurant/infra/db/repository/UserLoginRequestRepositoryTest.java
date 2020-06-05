package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequest;
import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenPublishedDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class UserLoginRequestRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    private UserLoginRequestRepository userLoginRequestRepository;
    
    @Value("${restaurant.login.expiration.hours}")
    int loginExpirationHours;

    private final String email = "iroha@example.com";
    private final MailAddress mailAddress = new MailAddress(email);

    private final RemoteIpAddress remoteIpAddr = new RemoteIpAddress("127.0.0.1");

    private RestaurantTempRegister tempRegister = new RestaurantTempRegister("いろは食堂", email);

    @Test
    void _飲食店ユーザーログイントークンの登録() throws Exception {

        RestaurantId restaurantId = restaurantRepository.save(tempRegister, remoteIpAddr);
        RestaurantAccount restaurantAccount = restaurantRepository.getAccountById(restaurantId);
        final UserId userId = restaurantAccount.getUserId();
        final AccessToken accessToken = new AccessToken();
        final AccessTokenPublishedDateTime publishedtDateTime = new AccessTokenPublishedDateTime();
        final AccessTokenExpirationDateTime expirationDateTime = publishedtDateTime.accessTokenExpirationDateTime(loginExpirationHours);
        
        // @formatter:off
        final LoginRequestId loginRequestId = userLoginRequestRepository.registerAccessToken(
                userId, accessToken, expirationDateTime, remoteIpAddr);
        // @formatter:on
        assertNotNull(loginRequestId);
        assertNotNull(loginRequestId.getValue());

        UserLoginRequest loginRequest = userLoginRequestRepository.getLoginRequest(loginRequestId);
        assertNotNull(loginRequest);
        
        UserId userIdAct = loginRequest.getUserId();
        assertNotNull(userIdAct);
        assertEquals(userId.getValue(), userIdAct.getValue());

        MailAddress mailAddressAct = loginRequest.getMailAddress();
        assertNotNull(mailAddressAct);
        assertEquals(mailAddress.getValue(), mailAddressAct.getValue());

        AccessToken accessTokenAct = loginRequest.getAccessToken();
        assertNotNull(accessTokenAct);
        assertEquals(accessToken.getValue(), accessTokenAct.getValue());

        AccessTokenExpirationDateTime expirationDateTimeAct = loginRequest.getAccessTokenExpirationDateTime();
        assertNotNull(expirationDateTimeAct);
        assertEquals(expirationDateTime.getValue(), expirationDateTimeAct.getValue());
    }
}
