package com.cheeringlocalrestaurant.usecase.restaurant;

public class RestaurantAccountAlreadyRegisteredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestaurantAccountAlreadyRegisteredException() {
        super();
    }

    public RestaurantAccountAlreadyRegisteredException(final String message) {
        super(message);
    }
}
