package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenExpiredException;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenNotFoundException;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenUpdatedException;
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
    private static final String remoteIpAddressStr = TestDataUtils.REMOTE_IP_ADDRESS;
    private static final RemoteIpAddress remoteIpAddress = TestDataUtils.remoteIpAddress;
    
    private AccessToken accessToken;
    private Timestamp timestamp;
    private LoginRequest loginRequest;
    
    @BeforeEach
    void setup() {
        accessToken = new AccessToken();
        LocalDateTime localDateTime = LocalDateTime.now();
        timestamp = Timestamp.valueOf(localDateTime.plusDays(1L));
        
        loginRequest = new LoginRequest();
        loginRequest.setUserId(1L);
        loginRequest.setAccessToken(accessToken.getValue());
        loginRequest.setTokenExpirationDatetime(timestamp);
        loginRequest.setRemoteIpAddress(remoteIpAddressStr);
        loginRequest.setRegisteredTimestamp(timestamp);
    }

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
        assertEquals(expirationDateTime.getValue(), loginRequest.getTokenExpirationDatetime().toLocalDateTime());
    }
    
    @Test
    void _存在するアクセストークンで検索した場合は対象のユーザーIDが返ってくる() throws Exception {
        
        try {
            loginRequestRepository.save(loginRequest);
            UserId userId = userLoginRequestRepository.findByAccessToken(accessToken);
            assertNotNull(userId);
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void _存在しないアクセストークンの場合は例外が発生する() {
        
        loginRequestRepository.deleteAll();
        assertThrows(LoginAccessTokenNotFoundException.class, () -> userLoginRequestRepository.findByAccessToken(new AccessToken()));
    }
    
    @Test
    void _有効期限の切れたアクセストークンの場合は例外が発生する() {
        
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.minusMinutes(1L);
        
        loginRequest.setTokenExpirationDatetime(Timestamp.valueOf(localDateTime));
        loginRequestRepository.save(loginRequest);

        assertThrows(LoginAccessTokenExpiredException.class, () -> userLoginRequestRepository.findByAccessToken(accessToken));
    }
    
    @Test
    void _登録日時のより新しいアクセストークンが存在する場合は例外が発生する() {
        
        loginRequestRepository.save(loginRequest);

        LocalDateTime newLocalDateTime = timestamp.toLocalDateTime();
        newLocalDateTime = newLocalDateTime.plusMinutes(1L);
        Timestamp newTimestamp = Timestamp.valueOf(newLocalDateTime);
        
        AccessToken newAccessToken = new AccessToken();
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setUserId(1L);
        newLoginRequest.setAccessToken(newAccessToken.getValue());
        newLoginRequest.setTokenExpirationDatetime(newTimestamp);
        newLoginRequest.setRemoteIpAddress(remoteIpAddressStr);
        newLoginRequest.setRegisteredTimestamp(newTimestamp);
        loginRequestRepository.save(newLoginRequest);

        assertThrows(LoginAccessTokenUpdatedException.class, () -> userLoginRequestRepository.findByAccessToken(accessToken));
    }
}
