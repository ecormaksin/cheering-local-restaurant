package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.io.IOException;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationHours;

import freemarker.template.TemplateException;

public interface RestaurantLoginURLNotifier {

    // @formatter:off
    void execute(HttpServletRequest request, 
            MailAddress mailaddress, 
            AccessToken accessToken,
            AccessTokenExpirationHours expirationHours) throws IOException, TemplateException;
    // @formatter:on
    
    Optional<MimeMessage[]> getMimeMessages();

}
