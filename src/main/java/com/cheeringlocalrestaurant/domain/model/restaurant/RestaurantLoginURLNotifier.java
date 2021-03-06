package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.SystemBaseURL;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationHours;

public interface RestaurantLoginURLNotifier {

    // @formatter:off
    void execute(SystemBaseURL systemBaseURL, 
            MailAddress mailaddress, 
            AccessToken accessToken,
            AccessTokenExpirationHours expirationHours) throws RestaurantLoginURLNotifyFailedException;
    // @formatter:on
    
    Optional<MimeMessage[]> getMimeMessages();

}
