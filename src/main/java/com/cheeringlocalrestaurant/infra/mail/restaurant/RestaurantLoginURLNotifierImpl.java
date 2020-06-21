package com.cheeringlocalrestaurant.infra.mail.restaurant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantLoginURLNotifier;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantLoginURLNotifyFailedException;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationHours;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;
import com.cheeringlocalrestaurant.infra.mail.FreeMarkerMailSender;

import freemarker.template.TemplateException;

@Service
public class RestaurantLoginURLNotifierImpl implements RestaurantLoginURLNotifier {

    @Autowired
    private FreeMarkerMailSender freeMarkerMailSender;

    @Autowired
    private MessageSource messagesource;

    @Value("${restaurant.notify.login.url.mail.template}")
    private String templateName;

    @Value("${mail.sender.name}")
    private String mailSenderName;

    @Override
    // @formatter:off
    public void execute(HttpServletRequest request, 
            MailAddress mailaddress, 
            AccessToken accessToken,
            AccessTokenExpirationHours expirationHours) throws RestaurantLoginURLNotifyFailedException {
    // @formatter:on

        try {
            Map<String, Object> model = new HashMap<>();
            final int remotePort = request.getRemotePort();
            final String remotePortString = (remotePort == 80 || remotePort == 443 ? "" : ":" + String.valueOf(remotePort));
            model.put("appRoot", request.getRequestURL() + remotePortString);
            model.put("token", accessToken.getValue());
            model.put("loginExpirationHours", expirationHours.getValue());
            model.put("mailSenderName", mailSenderName);

            final String subject = messagesource.getMessage("restaurant.notify.login.url.mail.subject", null, Locale.getDefault());

            // @formatter:off
            freeMarkerMailSender.send(mailaddress, 
                    new CustomMailSubject(subject), 
                    templateName, model);
            // @formatter:on

        } catch (IOException | TemplateException e) {
            throw new RestaurantLoginURLNotifyFailedException(e);
        }
    }

    @Override
    public Optional<MimeMessage[]> getMimeMessages() {
        return freeMarkerMailSender.getMimeMessages();
    }
}
