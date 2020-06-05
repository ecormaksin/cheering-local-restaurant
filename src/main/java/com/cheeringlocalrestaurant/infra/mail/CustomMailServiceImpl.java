package com.cheeringlocalrestaurant.infra.mail;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;

@Component
public class CustomMailServiceImpl implements CustomMailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    // @formatter:off
    public void send(CustomMailSender mailSender, 
            MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            CustomMailBody customMailBody) {
    // @formatter:on

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender.toString());
        message.setTo(mailAddress.getValue());
        message.setSubject(customMailSubject.getValue());
        message.setText(customMailBody.getValue());

        emailSender.send(message);
    }

    @Override
    public Optional<MimeMessage[]> getMimeMessages() {
        return null;
    }

}
