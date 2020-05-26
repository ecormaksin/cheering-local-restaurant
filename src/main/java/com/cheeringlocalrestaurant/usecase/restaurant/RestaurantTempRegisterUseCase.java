package com.cheeringlocalrestaurant.usecase.restaurant;

import com.cheeringlocalrestaurant.domain.model.restaurant.MailAddress;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantName;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantTempRegisterUseCase {
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	public RestaurantId execute(RestaurantTempRegister restaurantTempRegister, String remoteIpAddress) {
        return restaurantRepository.save(restaurantTempRegister, remoteIpAddress);
    }
}
