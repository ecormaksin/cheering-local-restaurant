package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.time.LocalDate;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

public interface RestaurantRepository {

    RestaurantAccount findByMailAddress(MailAddress mailAddress);

    RestaurantAccount findByMailAddress(MailAddress mailAddress, LocalDate targetDate);

    Restaurant findById(RestaurantId restaurantId);

    RestaurantAccount findAccountById(RestaurantId restaurantId);

    boolean doesExist(MailAddress mailAddress);

    RestaurantId save(RestaurantTempRegister tempRegister, String remoteIpAddress);
}
