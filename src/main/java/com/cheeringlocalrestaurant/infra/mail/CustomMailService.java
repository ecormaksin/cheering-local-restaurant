package com.cheeringlocalrestaurant.infra.mail;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;

public interface CustomMailService {

    CustomMailSender getDefaultMailSender();
    
    // @formatter:off
    void send(MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            CustomMailBody customMailBody);
    // @formatter:on

    // @formatter:off
    void send(CustomMailSender mailSender, 
            MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            CustomMailBody customMailBody);
    // @formatter:on

    Optional<MimeMessage[]> getMimeMessages();
}
