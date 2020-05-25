package com.cheeringlocalrestaurant.domain.model.restaurant;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class RestaurantTempRegister {

	@NonNull
	private RestaurantName name;
	@NonNull
	private MailAddress mailAddress;

	public RestaurantTempRegister(String name, String mail) {
		this.name = new RestaurantName(name);
		this.mailAddress = new MailAddress(mail);
	}
}
