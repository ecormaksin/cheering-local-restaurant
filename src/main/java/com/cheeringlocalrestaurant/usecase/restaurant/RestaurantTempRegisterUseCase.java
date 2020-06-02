package com.cheeringlocalrestaurant.usecase.restaurant;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantTempRegisterUseCase {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantId execute(RestaurantTempRegister restaurantTempRegister, String remoteIpAddress) {
        RestaurantId restaurantId = restaurantRepository.save(restaurantTempRegister, remoteIpAddress);
        return restaurantId;
    }
}
