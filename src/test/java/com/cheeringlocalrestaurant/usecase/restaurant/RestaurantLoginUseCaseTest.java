package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTopPageInfo;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTopPageInfoCreator;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;

@SpringBootTest
class RestaurantLoginUseCaseTest {

    @Autowired
    private RestaurantLoginUseCase restaurantLoginUseCase;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private UserLoginRequestRepository userLoginRequestRepository;

    @Test
    void _システムから発行されたトークンでアクセスした場合は対象飲食店のトップページ情報を返す() {
        
        final UserId userId = new UserId(1L);
        final AccessToken accessToken = new AccessToken();
        final RestaurantTopPageInfo expected = RestaurantTopPageInfoCreator.dummySimpleNoOrders();
        
        
        // @formatter:off
        given(userLoginRequestRepository.findByAccessToken(accessToken)).willReturn(userId);
        given(restaurantRepository.getTopPageInfo(userId)).willReturn(expected);
        // @formatter:on
        
        RestaurantTopPageInfo actual = restaurantLoginUseCase.execute(accessToken);
        
        assertEquals(expected, actual);
    }

}
