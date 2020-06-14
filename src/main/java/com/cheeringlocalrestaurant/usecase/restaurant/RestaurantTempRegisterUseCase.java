package com.cheeringlocalrestaurant.usecase.restaurant;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

@Service
public class RestaurantTempRegisterUseCase {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantId execute(final RestaurantTempRegister restaurantTempRegister, final RemoteIpAddress remoteIpAddress) {

        final Optional<RestaurantAccount> accountOpt = restaurantRepository.findAccountByMailAddress(restaurantTempRegister.getMailAddress());
        accountOpt.ifPresent(s -> {throw new RestaurantAccountAlreadyRegisteredException();});
        
        final RestaurantId restaurantId = restaurantRepository.save(restaurantTempRegister, remoteIpAddress);
        return restaurantId;
    }
}
