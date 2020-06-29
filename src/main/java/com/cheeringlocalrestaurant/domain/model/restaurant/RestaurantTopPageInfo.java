package com.cheeringlocalrestaurant.domain.model.restaurant;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.model.order.Order;
import com.cheeringlocalrestaurant.domain.type.CustomListCollection;
import com.cheeringlocalrestaurant.domain.type.date.CloseDay;
import com.cheeringlocalrestaurant.domain.type.date.OpenDay;
import com.cheeringlocalrestaurant.domain.type.restaurant.OrderAcceptable;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantHistoryId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class RestaurantTopPageInfo {

    @NonNull
    @Valid
    private RestaurantId restaurantId;
    
    @NonNull
    @Valid
    private RestaurantHistoryId restaurantHistoryId;
    
    @Setter
    @NonNull
    @Valid
    private RestaurantName restaurantName;
    
    @Setter
    @NonNull
    @Valid
    private OrderAcceptable orderAcceptable;
    
    @Setter
    @Valid
    private CustomListCollection<OpenDay> tempOpenDaysWithinAWeek;

    @Setter
    @Valid
    private CustomListCollection<CloseDay> tempCloseDaysWithinAWeek;
    
    @Setter
    @Valid
    private CustomListCollection<Order> orders;
}
