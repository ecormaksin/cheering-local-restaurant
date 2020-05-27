package com.cheeringlocalrestaurant.domain.model.restaurant;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Restaurant {

	private RestaurantId restaurantId;
	private RestaurantName restaurantName;
	private MailAddress mailAddress;
}
