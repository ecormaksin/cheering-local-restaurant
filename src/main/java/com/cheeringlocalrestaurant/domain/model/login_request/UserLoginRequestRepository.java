package com.cheeringlocalrestaurant.domain.model.login_request;

import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;

public interface UserLoginRequestRepository {

    // @formatter:off
    LoginRequestId registerAccessToken(UserId userId, 
            AccessToken accessToken, 
            AccessTokenExpirationDateTime expirationDateTime, 
            RemoteIpAddress remoteIpAddress);
    // @formatter:on

    UserLoginRequest getLoginRequest(LoginRequestId loginTokenId) throws UserLoginRequestNotFoundException;

    UserId findByAccessToken(AccessToken accessToken);
}
