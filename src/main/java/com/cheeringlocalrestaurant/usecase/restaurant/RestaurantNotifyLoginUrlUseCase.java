package com.cheeringlocalrestaurant.usecase.restaurant;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenExpirationDateTime;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenId;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenPublishedDateTime;

@Service
@Transactional
public class RestaurantNotifyLoginUrlUseCase {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Value("${restaurant.login.expiration.hours}")
    private int loginExpirationHours;

    public AccessTokenId execute(final MailAddress mailAddress, final RemoteIpAddress remoteIpAddress) {
        
        final Optional<RestaurantAccount> accountOpt = restaurantRepository.findAccountByMailAddress(mailAddress);
        accountOpt.orElseThrow(RestaurantAccountNotRegisteredException::new);
        
        final RestaurantAccount restaurantAccount = accountOpt.get();
        final AccessToken accessToken = new AccessToken();
        final AccessTokenPublishedDateTime publishedtDateTime = new AccessTokenPublishedDateTime();
        final AccessTokenExpirationDateTime expirationDateTime = publishedtDateTime.accessTokenExpirationDateTime(loginExpirationHours);
        // @formatter:off
        final AccessTokenId accessTokenId = restaurantRepository.registerAccessToken(
                                                restaurantAccount.getUserId(), 
                                                accessToken, expirationDateTime, remoteIpAddress);
        // @formatter:on
        
        return accessTokenId;
    }
}
