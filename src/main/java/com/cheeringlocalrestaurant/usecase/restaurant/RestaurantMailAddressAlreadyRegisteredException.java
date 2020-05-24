package com.cheeringlocalrestaurant.usecase.restaurant;

public class RestaurantMailAddressAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RestaurantMailAddressAlreadyRegisteredException() {
        super();
    }

    public RestaurantMailAddressAlreadyRegisteredException(final String message) {
        super(message);
    }
}
