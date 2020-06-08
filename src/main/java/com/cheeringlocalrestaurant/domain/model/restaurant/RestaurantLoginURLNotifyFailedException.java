package com.cheeringlocalrestaurant.domain.model.restaurant;

public class RestaurantLoginURLNotifyFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    public RestaurantLoginURLNotifyFailedException(Exception e) {
        super(e);
    }

    public RestaurantLoginURLNotifyFailedException() {
        super();
    }
}
