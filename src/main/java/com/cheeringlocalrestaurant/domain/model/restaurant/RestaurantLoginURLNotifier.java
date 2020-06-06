package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationHours;

public interface RestaurantLoginURLNotifier {

    // @formatter:off
    void execute(HttpServletRequest request, 
            MailAddress mailaddress, 
            AccessToken accessToken,
            AccessTokenExpirationHours expirationHours);
    // @formatter:on
    
    Optional<MimeMessage[]> getMimeMessages();

}
