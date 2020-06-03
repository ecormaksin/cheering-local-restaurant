package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.cheeringlocalrestaurant.domain.model.LoginAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.type.account.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenId;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenPublishedDateTime;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RestaurantNotifyLoginUrlUseCaseTest {

    @Autowired
    RestaurantNotifyLoginUrlUseCase restaurantNotifyLoginUrlUseCase;
    
    @MockBean
    private RestaurantRepository restaurantRepository;

    private final String email = "iroha@example.com";
    private final MailAddress mailAddress = new MailAddress(email);
    private final String remoteIPAddr = "127.0.0.1";

    @Test
    void _ログイン用のアクセストークンが作成されること() {
        // @formatter:off
        LoginAccount accountExp = LoginAccount.builder()
                                            .userId(new UserId(1L))
                                            .mailAddress(mailAddress)
                                            .accessToken(new AccessToken(UUID.randomUUID().toString()))
                                            .accessTokenExpirationDateTime(new AccessTokenExpirationDateTime(LocalDateTime.now()))
                                            .build();
        given(restaurantRepository.getLoginAccount((AccessTokenId) any())).willReturn(accountExp);

        // @formatter:on
        AccessTokenId accessTokenId = restaurantNotifyLoginUrlUseCase.execute(mailAddress, remoteIPAddr);
        assertNotNull(accessTokenId);
        
        LoginAccount loginAccount = restaurantRepository.getLoginAccount(accessTokenId);
        assertNotNull(loginAccount);
        assertNotNull(loginAccount.getUserId());
        assertNotNull(loginAccount.getMailAddress());
        assertNotNull(loginAccount.getAccessToken());
        assertNotNull(loginAccount.getAccessTokenExpirationDateTime());
    }

}
