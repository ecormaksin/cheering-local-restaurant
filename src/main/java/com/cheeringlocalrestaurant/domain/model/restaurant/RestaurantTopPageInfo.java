package com.cheeringlocalrestaurant.domain.model.restaurant;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.model.order.Order;
import com.cheeringlocalrestaurant.domain.type.CustomGenericCollection;
import com.cheeringlocalrestaurant.domain.type.date.CloseDay;
import com.cheeringlocalrestaurant.domain.type.date.OpenDay;
import com.cheeringlocalrestaurant.domain.type.restaurant.OrderAcceptable;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantHistoryId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTopPageInfo {

    @NonNull
    @Valid
    private RestaurantId restaurantId;
    
    @NonNull
    @Valid
    private RestaurantHistoryId restaurantHistoryId;
    
    @NonNull
    @Valid
    private RestaurantName restaurantName;
    
    @NonNull
    @Valid
    private OrderAcceptable orderAcceptable;
    
    @Valid
    private CustomGenericCollection<OpenDay> tempOpenDays;

    @Valid
    private CustomGenericCollection<CloseDay> tempCloseDays;
    
    @Valid
    private CustomGenericCollection<Order> orders;
}
