package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantLoginUseCase;

@WebMvcTest(RestaurantLoginController.class)
class RestaurantLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantLoginUseCase restaurantLoginUseCase;
    
    @Test
    void _システムから発行されたトークンでアクセスした場合は飲食店のトップページへ遷移する() throws Exception {

        AccessToken accessToken = new AccessToken();
        
        this.mockMvc.perform(get("/" + RestaurantLoginController.PATH_BASE + "?accesstoken=" + accessToken.getValue()))
            .andExpect(status().isOk())
            .andExpect(view().name(RestaurantRegisteredController.VIEW_TOP));
    }

}
