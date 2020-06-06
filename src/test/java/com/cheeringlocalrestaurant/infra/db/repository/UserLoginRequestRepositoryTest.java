package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

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
import com.cheeringlocalrestaurant.infra.db.JavaTimeDateConverter;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.LoginRequest;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.LoginRequestRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
class UserLoginRequestRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    private UserLoginRequestRepository userLoginRequestRepository;
    
    @Autowired
    private LoginRequestRepository loginRequestRepository;
    
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
        final Long loginRequestIdLng = loginRequestId.getValue();
        assertNotNull(loginRequestIdLng);

        UserLoginRequest userLoginRequest = userLoginRequestRepository.getLoginRequest(loginRequestId);
        assertNotNull(userLoginRequest);
        
        UserId userIdAct = userLoginRequest.getUserId();
        assertEquals(userId.getValue(), userIdAct.getValue());

        MailAddress mailAddressAct = userLoginRequest.getMailAddress();
        assertEquals(mailAddressStr, mailAddressAct.getValue());

        AccessToken accessTokenAct = userLoginRequest.getAccessToken();
        assertEquals(accessToken.getValue(), accessTokenAct.getValue());

        AccessTokenExpirationDateTime expirationDateTimeAct = userLoginRequest.getAccessTokenExpirationDateTime();
        assertEquals(expirationDateTime.getValue(), expirationDateTimeAct.getValue());

        // ** エンティティの確認
        LoginRequest loginRequest = loginRequestRepository.getOne(loginRequestIdLng);
        assertNotNull(loginRequest);
        assertEquals(userId.getValue(), loginRequest.getUserId());
        assertEquals(accessToken.getValue(), loginRequest.getAccessToken());
        assertEquals(expirationDateTime.getValue(), JavaTimeDateConverter.toLocalDateTimeFromTimestamp(loginRequest.getTokenExpirationDatetime()));
    }
}
