package com.cheeringlocalrestaurant.domain.model.restaurant;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
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
	private RestaurantId id;
	@NonNull
	@Valid
	private RestaurantName name;
	@NonNull
	@Valid
	private MailAddress mailAddress;
}
