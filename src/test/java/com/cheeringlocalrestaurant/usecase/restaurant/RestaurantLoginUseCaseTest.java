package com.cheeringlocalrestaurant.usecase.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTopPageInfo;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;

@SpringBootTest
class RestaurantLoginUseCaseTest {

    @Autowired
    private RestaurantLoginUseCase restaurantLoginUseCase;

    @Test
    void _システムから発行されたトークンでアクセスした場合は対象飲食店のトップページ情報を返す() {
        
        final AccessToken accessToken = new AccessToken();
        
        RestaurantTopPageInfo expected;
        
        RestaurantTopPageInfo actual = restaurantLoginUseCase.execute(accessToken);
    }

}
