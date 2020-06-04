package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.util.Optional;

import com.cheeringlocalrestaurant.domain.model.UserLoginRequest;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.infra.db.repository.LoginRequestNotFoundException;
import com.cheeringlocalrestaurant.infra.db.repository.RestaurantAccountNotFoundException;

public interface RestaurantRepository {

    Optional<RestaurantAccount> findAccountByMailAddress(MailAddress mailAddress);

    Restaurant findById(RestaurantId restaurantId);

    RestaurantAccount getAccountById(RestaurantId restaurantId) throws RestaurantAccountNotFoundException;

    RestaurantId save(RestaurantTempRegister tempRegister, RemoteIpAddress remoteIpAddress);

    // @formatter:off
    AccessTokenId registerAccessToken(UserId userId, 
            AccessToken accessToken, 
            AccessTokenExpirationDateTime expirationDateTime, 
            RemoteIpAddress remoteIpAddress);
    // @formatter:on

    UserLoginRequest getLoginRequest(AccessTokenId loginTokenId) throws LoginRequestNotFoundException;
}
