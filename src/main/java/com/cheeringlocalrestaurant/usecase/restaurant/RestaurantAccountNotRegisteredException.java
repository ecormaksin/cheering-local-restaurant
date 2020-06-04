package com.cheeringlocalrestaurant.usecase.restaurant;

public class RestaurantAccountNotRegisteredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestaurantAccountNotRegisteredException() {
        super();
    }

    public RestaurantAccountNotRegisteredException(final String message) {
        super(message);
    }
}
