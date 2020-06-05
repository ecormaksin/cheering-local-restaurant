package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cheeringlocalrestaurant.TestDataUtils;
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
    private int loginExpirationHours;
    
    private static final String mailAddressStr = TestDataUtils.MAIL_ADDRESS;
    private static final RestaurantTempRegister restaurantTempRegister = TestDataUtils.restaurantTempRegister;
    private static final RemoteIpAddress remoteIpAddress = TestDataUtils.remoteIpAddress;

    @Test
    void _飲食店ユーザーログイントークンの登録() throws Exception {

        RestaurantId restaurantId = restaurantRepository.save(restaurantTempRegister, remoteIpAddress);
        RestaurantAccount restaurantAccount = restaurantRepository.getAccountById(restaurantId);
        final UserId userId = restaurantAccount.getUserId();
        final AccessToken accessToken = new AccessToken();
        final AccessTokenPublishedDateTime publishedtDateTime = new AccessTokenPublishedDateTime();
        final AccessTokenExpirationDateTime expirationDateTime = publishedtDateTime.accessTokenExpirationDateTime(loginExpirationHours);
        
        // @formatter:off
        final LoginRequestId loginRequestId = userLoginRequestRepository.registerAccessToken(
                userId, accessToken, expirationDateTime, remoteIpAddress);
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
        assertEquals(mailAddressStr, mailAddressAct.getValue());

        AccessToken accessTokenAct = loginRequest.getAccessToken();
        assertNotNull(accessTokenAct);
        assertEquals(accessToken.getValue(), accessTokenAct.getValue());

        AccessTokenExpirationDateTime expirationDateTimeAct = loginRequest.getAccessTokenExpirationDateTime();
        assertNotNull(expirationDateTimeAct);
        assertEquals(expirationDateTime.getValue(), expirationDateTimeAct.getValue());
    }
}
