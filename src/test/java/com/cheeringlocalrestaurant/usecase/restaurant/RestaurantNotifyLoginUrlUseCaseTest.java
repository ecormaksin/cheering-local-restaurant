package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;

import com.cheeringlocalrestaurant.CustomMailServiceTestConfig;
import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantLoginURLNotifier;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationHours;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

@SpringBootTest
@Import(CustomMailServiceTestConfig.class)
class RestaurantNotifyLoginUrlUseCaseTest {

    @Autowired
    RestaurantNotifyLoginUrlUseCase restaurantNotifyLoginUrlUseCase;

    @Autowired
    private MockHttpServletRequest request;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private UserLoginRequestRepository userLoginRequestRepository;

    @MockBean
    private RestaurantLoginURLNotifier restaurantLoginURLNotifier;

    private static final MailAddress mailAddress = TestDataUtils.mailAddress;
    private static final RemoteIpAddress remoteIpAddress = TestDataUtils.remoteIpAddress;

    @Test
    void _メールアドレスが登録済の場合はログイン用のアクセストークンが作成されること() {
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
            // @formatter:off
            doNothing().when(restaurantLoginURLNotifier).execute((HttpServletRequest) any(), 
                                                    (MailAddress) any(), 
                                                    (AccessToken) any(), 
                                                    (AccessTokenExpirationHours) any());
            // @formatter:on
    
            LoginRequestId loginRequestId = restaurantNotifyLoginUrlUseCase.execute(request, mailAddress, remoteIpAddress);
            assertNotNull(loginRequestId);

        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void _メールアドレスが未登録の場合は例外が発生すること() {
        
        given(restaurantRepository.findAccountByMailAddress((MailAddress) any())).willReturn(Optional.ofNullable(null));
        
        assertThrows(RestaurantAccountNotRegisteredException.class, () -> {
            restaurantNotifyLoginUrlUseCase.execute(request, mailAddress, remoteIpAddress);
        });
    }
}
