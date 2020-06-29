package com.cheeringlocalrestaurant.domain.model.restaurant;

import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.type.restaurant.OrderAcceptable;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantHistoryId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

public final class RestaurantTopPageInfoCreator {

    public static RestaurantTopPageInfo dummySimpleNoOrders() {

        // @formatter:off
        return RestaurantTopPageInfo.builder()
                .restaurantId(new RestaurantId(1L))
                .restaurantHistoryId(new RestaurantHistoryId(1L))
                .restaurantName(new RestaurantName(TestDataUtils.RESTAURANT_NAME))
                .orderAcceptable(OrderAcceptable.NO)
                .build();
        // @formatter:on
    }
}
