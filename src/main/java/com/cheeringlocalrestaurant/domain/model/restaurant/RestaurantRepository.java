package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.util.Optional;

import com.cheeringlocalrestaurant.domain.model.UserLoginRequest;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.infra.db.repository.LoginRequestNotFoundException;
import com.cheeringlocalrestaurant.infra.db.repository.RestaurantAccountNotFoundException;

public interface RestaurantRepository {

    Optional<RestaurantAccount> findAccountByMailAddress(MailAddress mailAddress);

    Restaurant findById(RestaurantId restaurantId);

    RestaurantAccount getAccountById(RestaurantId restaurantId) throws RestaurantAccountNotFoundException;

    RestaurantId save(RestaurantTempRegister tempRegister, RemoteIpAddress remoteIpAddress);

    // @formatter:off
    LoginRequestId registerAccessToken(UserId userId, 
            AccessToken accessToken, 
            AccessTokenExpirationDateTime expirationDateTime, 
            RemoteIpAddress remoteIpAddress);
    // @formatter:on

    UserLoginRequest getLoginRequest(LoginRequestId loginTokenId) throws LoginRequestNotFoundException;
}
