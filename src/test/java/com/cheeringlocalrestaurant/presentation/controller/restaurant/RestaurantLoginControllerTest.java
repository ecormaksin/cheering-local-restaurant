package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;

import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.presentation.controller.BaseController;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantAccessTokenNotFoundException;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantLoginUseCase;

@WebMvcTest(RestaurantLoginController.class)
class RestaurantLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantLoginUseCase restaurantLoginUseCase;
    @MockBean
    private MessageSource messagesource;
    
    @Test
    void _システムから発行されたトークンでアクセスした場合は飲食店のトップページへ遷移する() throws Exception {

        final String accessToken = UUID.randomUUID().toString();
        
        // @formatter:off
        this.mockMvc.perform(get(RestaurantLoginController.PATH_BASE + "?accesstoken=" + accessToken))
            .andExpect(status().isOk())
            .andExpect(view().name(RestaurantRegisteredController.VIEW_TOP));
        // @formatter:on
    }
    
    @Test
    void _トークンなしでアクセスした場合はエラーページへ遷移する() throws Exception {

        testWhenValidTokenNotIncluded(RestaurantLoginController.PATH_BASE);
    }
    
    @Test
    void _システムで発行されていないトークンでアクセスした場合はエラーページへ遷移する() throws Exception {

        final String accessToken = UUID.randomUUID().toString();
        doThrow(new RestaurantAccessTokenNotFoundException()).when(restaurantLoginUseCase).execute(new AccessToken(accessToken));

        testWhenValidTokenNotIncluded(RestaurantLoginController.PATH_BASE + "?accesstoken=" + accessToken);
    }
    
    private void testWhenValidTokenNotIncluded(String path) throws Exception {
        
        final String errorMessage = "ログインリクエストで受信したメールからログインしてください。";
        final String linkCaption = "ログインリクエストする";
        doReturn(errorMessage).when(messagesource).getMessage("message.login.validTokenNotIncluded", null,
                Locale.getDefault());
        doReturn(linkCaption).when(messagesource).getMessage("label.login.request", null,
                Locale.getDefault());
        
        // @formatter:off
        this.mockMvc.perform(get(path))
            .andExpect(status().isOk())
            .andExpect(model().attribute("errorMessage", errorMessage))
            .andExpect(model().attribute("originalPageLink", RestaurantLoginRequestController.PATH_BASE))
            .andExpect(model().attribute("linkCaption", linkCaption))
            .andExpect(view().name(BaseController.CUSTOM_ERROR_PAGE_PATH));
        // @formatter:on
    }

}
