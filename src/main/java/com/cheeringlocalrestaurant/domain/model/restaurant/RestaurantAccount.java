package com.cheeringlocalrestaurant.domain.model.restaurant;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.UserId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class RestaurantAccount {

	@NonNull
	@Valid
	private UserId userId;
	@NonNull
	@Valid
	private RestaurantId restaurantId;
	@NonNull
	@Valid
	private RestaurantName restaurantName;
	@NonNull
	@Valid
	private MailAddress mailAddress;
}
