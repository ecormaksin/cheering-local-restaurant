package com.cheeringlocalrestaurant.usecase.restaurant;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheeringlocalrestaurant.domain.model.login_request.UserLoginRequestRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenPublishedDateTime;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;

@Service
@Transactional
public class RestaurantNotifyLoginUrlUseCase {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    private UserLoginRequestRepository userLoginRequestRepository;

    @Value("${restaurant.login.expiration.hours}")
    private int loginExpirationHours;

    public LoginRequestId execute(final MailAddress mailAddress, final RemoteIpAddress remoteIpAddress) {
        
        final Optional<RestaurantAccount> accountOpt = restaurantRepository.findAccountByMailAddress(mailAddress);
        accountOpt.orElseThrow(RestaurantAccountNotRegisteredException::new);
        
        final RestaurantAccount restaurantAccount = accountOpt.get();
        final AccessToken accessToken = new AccessToken();
        final AccessTokenPublishedDateTime publishedtDateTime = new AccessTokenPublishedDateTime();
        final AccessTokenExpirationDateTime expirationDateTime = publishedtDateTime.accessTokenExpirationDateTime(loginExpirationHours);
        // @formatter:off
        final LoginRequestId loginRequestId = userLoginRequestRepository.registerAccessToken(
                                                restaurantAccount.getUserId(), 
                                                accessToken, expirationDateTime, remoteIpAddress);
        // @formatter:on
        
        return loginRequestId;
    }
}
