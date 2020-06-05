package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestRepository;
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

    @MockBean
    private UserLoginRequestRepository userLoginRequestRepository;

    private final MailAddress mailAddress = new MailAddress("iroha@example.com");
    private final RemoteIpAddress remoteIpAddr = new RemoteIpAddress("127.0.0.1");

    @Test
    void _メールアドレスが登録済みの場合はログイン用のアクセストークンが作成されること() {
        try {
            UserId userId = new UserId(1L);
            RestaurantId restaurantId = new RestaurantId(1L);
            RestaurantAccount restaurantAccount = new RestaurantAccount(userId, restaurantId, mailAddress);
            // @formatter:off
            given(userLoginRequestRepository.registerAccessToken(
                                            (UserId) any(),
                                            (AccessToken) any(),
                                            (AccessTokenExpirationDateTime) any(),
                                            (RemoteIpAddress) any())
                    ).willReturn(new LoginRequestId(1L));
            // @formatter:on
            given(restaurantRepository.findAccountByMailAddress((MailAddress) any())).willReturn(Optional.ofNullable(restaurantAccount));
    
            LoginRequestId loginRequestId = restaurantNotifyLoginUrlUseCase.execute(mailAddress, remoteIpAddr);
            assertNotNull(loginRequestId);

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
