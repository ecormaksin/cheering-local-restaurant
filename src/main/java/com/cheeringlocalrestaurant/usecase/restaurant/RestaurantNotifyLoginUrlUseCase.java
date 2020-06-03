package com.cheeringlocalrestaurant.usecase.restaurant;

import org.springframework.stereotype.Service;

import com.cheeringlocalrestaurant.domain.type.account.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenId;
import com.cheeringlocalrestaurant.domain.type.account.access_token.AccessTokenPublishedDateTime;

@Service
public class RestaurantNotifyLoginUrlUseCase {

    public AccessTokenId execute(MailAddress mailAddress, String remoteIPAddr) {
        return new AccessTokenId(1L);
    }

}
