package com.cheeringlocalrestaurant.domain.model.restaurant;

import com.cheeringlocalrestaurant.domain.model.LoginAccount;
import com.cheeringlocalrestaurant.domain.type.account.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenId;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenPublishedDateTime;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

public interface RestaurantRepository {

    RestaurantAccount findByMailAddress(MailAddress mailAddress);

    Restaurant findById(RestaurantId restaurantId);

    RestaurantAccount findAccountById(RestaurantId restaurantId);

    boolean doesExist(MailAddress mailAddress);

    RestaurantId save(RestaurantTempRegister tempRegister, String remoteIpAddress);

    AccessTokenId registerAccessToken(MailAddress mailAddress, AccessTokenExpirationDateTime expirationDateTime);

    LoginAccount getLoginAccount(AccessTokenId loginTokenId);
}
