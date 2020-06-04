package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cheeringlocalrestaurant.domain.model.UserLoginRequest;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RestaurantNotifyLoginUrlUseCaseTest {

    @Autowired
    RestaurantNotifyLoginUrlUseCase restaurantNotifyLoginUrlUseCase;

    @MockBean
    private RestaurantRepository restaurantRepository;

    private final String email = "iroha@example.com";
    private final MailAddress mailAddress = new MailAddress(email);
    private final RemoteIpAddress remoteIpAddr = new RemoteIpAddress("127.0.0.1");

    @Test
    void _メールアドレスが登録済みの場合はログイン用のアクセストークンが作成されること() {
        try {
            UserId userId = new UserId(1L);
            RestaurantId restaurantId = new RestaurantId(1L);
            RestaurantAccount restaurantAccount = new RestaurantAccount(userId, restaurantId, mailAddress);
            AccessToken accessToken = new AccessToken();
            AccessTokenExpirationDateTime accessTokenExpirationDateTime = new AccessTokenExpirationDateTime(LocalDateTime.now());
            // @formatter:off
            UserLoginRequest loginRequestExp = UserLoginRequest.builder()
                        .userId(userId)
                        .mailAddress(mailAddress)
                        .accessToken(accessToken)
                        .accessTokenExpirationDateTime(accessTokenExpirationDateTime)
                    .build();
            given(restaurantRepository.registerAccessToken(
                                            (UserId) any(),
                                            (AccessToken) any(),
                                            (AccessTokenExpirationDateTime) any(),
                                            (RemoteIpAddress) any())
                    ).willReturn(new LoginRequestId(1L));
            // @formatter:on
            given(restaurantRepository.findAccountByMailAddress((MailAddress) any())).willReturn(Optional.ofNullable(restaurantAccount));
            given(restaurantRepository.getLoginRequest((LoginRequestId) any())).willReturn(loginRequestExp);
    
            LoginRequestId loginRequestId = restaurantNotifyLoginUrlUseCase.execute(mailAddress, remoteIpAddr);
            assertNotNull(loginRequestId);
            
            UserLoginRequest loginAccount = restaurantRepository.getLoginRequest(loginRequestId);
            assertNotNull(loginAccount);
            assertNotNull(loginAccount.getUserId());
            assertNotNull(loginAccount.getMailAddress());
            assertNotNull(loginAccount.getAccessToken());
            assertNotNull(loginAccount.getAccessTokenExpirationDateTime());
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void _メールアドレスが未登録の場合は例外が発生すること() {
        
        given(restaurantRepository.findAccountByMailAddress((MailAddress) any())).willReturn(Optional.ofNullable(null));
        
        assertThrows(RestaurantAccountNotRegisteredException.class, () -> {
            restaurantNotifyLoginUrlUseCase.execute(mailAddress, remoteIpAddr);
        });
    }
}
