package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.util.Optional;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

public interface RestaurantRepository {

    Optional<RestaurantAccount> findAccountByMailAddress(MailAddress mailAddress);

    Restaurant findById(RestaurantId restaurantId);

    RestaurantAccount getAccountById(RestaurantId restaurantId) throws RestaurantAccountNotFoundException;

    RestaurantId save(RestaurantTempRegister tempRegister, RemoteIpAddress remoteIpAddress);

    RestaurantTopPageInfo getTopPageInfo(UserId userId);
}
