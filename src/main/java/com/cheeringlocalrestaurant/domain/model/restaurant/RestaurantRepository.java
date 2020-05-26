package com.cheeringlocalrestaurant.domain.model.restaurant;

public interface RestaurantRepository {

	RestaurantAccount findByMailAddress(String mailAddress);

	Restaurant findById(RestaurantId restaurantId);
	
	RestaurantAccount findAccountById(RestaurantId restaurantId);

	RestaurantId save(RestaurantTempRegister tempRegister);
}
