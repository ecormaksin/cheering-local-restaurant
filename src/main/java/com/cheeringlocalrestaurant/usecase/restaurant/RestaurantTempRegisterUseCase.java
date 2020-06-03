package com.cheeringlocalrestaurant.usecase.restaurant;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantTempRegisterUseCase {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantId execute(final RestaurantTempRegister restaurantTempRegister, final String remoteIpAddress) {

        final boolean doesExist = restaurantRepository.doesExist(restaurantTempRegister.getMailAddress());
        if (doesExist) throw new RestaurantMailAddressAlreadyRegisteredException();

        final RestaurantId restaurantId = restaurantRepository.save(restaurantTempRegister, remoteIpAddress);
        return restaurantId;
    }
}
