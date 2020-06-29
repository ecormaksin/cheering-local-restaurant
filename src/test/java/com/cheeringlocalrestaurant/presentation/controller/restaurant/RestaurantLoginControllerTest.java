package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;
import java.util.UUID;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;

import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenExpiredException;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenNotFoundException;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenUpdatedException;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTopPageInfoCreator;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.presentation.controller.BaseController;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantLoginUseCase;

@WebMvcTest(RestaurantLoginController.class)
class RestaurantLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantLoginUseCase restaurantLoginUseCase;
    @MockBean
    private MessageSource messagesource;
    
    @Value("${mail.sender.address}")
    private String mailSenderAddress;
    
    private enum SetAccessToken {
        TRUE(() -> "?accesstoken=" + UUID.randomUUID().toString()),
        FALSE(() -> "");
        
        Supplier<String> function;
        
        private SetAccessToken(Supplier<String> function) {
            this.function = function;
        }
        
        public String getParam() {
            return this.function.get();
        }
    }
    
    private void testWhenValidTokenNotIncluded(SetAccessToken setAccessToken) throws Exception {

        final String errorMessage = "メールに記載されたURLからログインしてください。";
        given(messagesource.getMessage("message.login.validTokenNotIncluded", null, Locale.getDefault())).willReturn(errorMessage);

        testWhenInvalidToken(setAccessToken, errorMessage);
    }
    
    private void testWhenInvalidToken(final String errorMessage) throws Exception {
        testWhenInvalidToken(SetAccessToken.TRUE, errorMessage);
    }
    
    private void testWhenInvalidToken(final SetAccessToken setAccessToken, final String errorMessage) throws Exception {
        
        final String linkCaption = "ログインリクエストする";
        given(messagesource.getMessage("label.login.request", null, Locale.getDefault())).willReturn(linkCaption);
        
        final String param = setAccessToken.getParam();

        // @formatter:off
        this.mockMvc.perform(get(RestaurantLoginController.PATH_BASE + param))
            .andExpect(status().isOk())
            .andExpect(model().attribute("errorMessage", errorMessage))
            .andExpect(model().attribute("originalPageLink", RestaurantLoginRequestController.PATH_BASE))
            .andExpect(model().attribute("linkCaption", linkCaption))
            .andExpect(view().name(BaseController.CUSTOM_ERROR_PAGE_PATH));
        // @formatter:on
    }
    
    @Test
    void _システムから発行されたトークンでアクセスした場合は飲食店のトップページへ遷移する() throws Exception {

        given(restaurantLoginUseCase.execute((AccessToken) any())).willReturn(RestaurantTopPageInfoCreator.dummySimpleNoOrders());
        
        final String accessToken = UUID.randomUUID().toString();
        // @formatter:off
        this.mockMvc.perform(get(RestaurantLoginController.PATH_BASE + "?accesstoken=" + accessToken))
            .andExpect(status().isOk())
            .andExpect(view().name(RestaurantRegisteredController.VIEW_TOP));
        // @formatter:on
    }
    
    @Test
    void _トークンなしでアクセスした場合はエラーページへ遷移する() throws Exception {

        testWhenValidTokenNotIncluded(SetAccessToken.FALSE);
    }
    
    @Test
    void _システムで発行されていないトークンでアクセスした場合はエラーページへ遷移する() throws Exception {

        given(restaurantLoginUseCase.execute((AccessToken) any())).willThrow(new LoginAccessTokenNotFoundException());

        testWhenValidTokenNotIncluded(SetAccessToken.TRUE);
    }
    
    @Test
    void _トークンの有効期限が切れている場合はエラーページへ遷移する() throws Exception {
        
        final String errorMessage = "ログインURLの有効期限が切れています。\nログインリクエストを行ってください。";
        given(messagesource.getMessage("message.login.tokenExpired", null, Locale.getDefault())).willReturn(errorMessage);

        given(restaurantLoginUseCase.execute((AccessToken) any())).willThrow(new LoginAccessTokenExpiredException());

        testWhenInvalidToken(errorMessage);
    }
    
    @Test
    void _より新しいログインリクエストが行われている場合はエラーページへ遷移する() throws Exception {
        
        final String errorMessage = "新しいログインリクエストが行われています。メールを確認してください。\n身に覚えがない場合は速やかにログインリクエストを行い、" + mailSenderAddress + "までお問い合わせください。";
        given(messagesource.getMessage("message.login.tokenUpdated", new Object[] {mailSenderAddress}, Locale.getDefault())).willReturn(errorMessage);

        given(restaurantLoginUseCase.execute((AccessToken) any())).willThrow(new LoginAccessTokenUpdatedException());

        testWhenInvalidToken(errorMessage);
    }
}
