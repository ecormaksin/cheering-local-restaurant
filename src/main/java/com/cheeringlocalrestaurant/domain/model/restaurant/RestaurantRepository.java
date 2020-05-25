package com.cheeringlocalrestaurant.domain.model.restaurant;

public interface RestaurantRepository {

	RestaurantAccount findByMailAddress(String mailAddress);
}
