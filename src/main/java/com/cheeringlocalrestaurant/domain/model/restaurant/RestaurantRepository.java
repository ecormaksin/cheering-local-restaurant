package com.cheeringlocalrestaurant.domain.model.restaurant;

import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

public interface RestaurantRepository {

    RestaurantAccount findByMailAddress(String mailAddress);

    Restaurant findById(RestaurantId restaurantId);

    RestaurantAccount findAccountById(RestaurantId restaurantId);

    RestaurantId save(RestaurantTempRegister tempRegister, String remoteIpAddress);
}
