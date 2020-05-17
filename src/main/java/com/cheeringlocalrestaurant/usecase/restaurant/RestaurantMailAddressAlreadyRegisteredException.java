package com.cheeringlocalrestaurant.usecase.restaurant;

public class RestaurantMailAddressAlreadyRegisteredException extends RuntimeException {

    public RestaurantMailAddressAlreadyRegisteredException() {
        super();
    }

    public RestaurantMailAddressAlreadyRegisteredException(final String message) {
        super(message);
    }
}
