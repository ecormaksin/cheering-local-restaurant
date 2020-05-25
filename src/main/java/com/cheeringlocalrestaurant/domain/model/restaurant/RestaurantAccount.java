package com.cheeringlocalrestaurant.domain.model.restaurant;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class RestaurantAccount {

	@NonNull
	private RestaurantId id;
	@NonNull
	private RestaurantName name;
	@NonNull
	private MailAddress mailAddress;
}
