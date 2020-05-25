package com.cheeringlocalrestaurant.domain.model.restaurant;

import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository {

	RestaurantAccount findByMailAddress(String mailAddress);

	Restaurant findById(RestaurantId restaurantId);
	
	RestaurantAccount findAccountById(RestaurantId restaurantId);

	RestaurantId save(RestaurantTempRegister tempRegister);
}
