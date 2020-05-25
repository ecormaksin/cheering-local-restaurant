package com.cheeringlocalrestaurant.domain.model.restaurant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Restaurant {

	private RestaurantId restaurantId;
	private RestaurantName restaurantName;
	private MailAddress mailAddress;
}
