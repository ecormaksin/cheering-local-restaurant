package com.cheeringlocalrestaurant.infra.mail;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;

public class CustomMailServiceImpl implements CustomMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.sender.name}")
    private String mailSenderName;

    @Value("${mail.sender.address}")
    private String mailSenderAddress;

    private CustomMailSender mailSender;

    @PostConstruct
    void postConstruct() {
        mailSender = new CustomMailSender(mailSenderName, mailSenderAddress);
    }
    
    @Override
    public CustomMailSender getDefaultMailSender() {
        return mailSender;
    }

    @Override
    // @formatter:off
    public void send(MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            CustomMailBody customMailBody) {
    // @formatter:on
        send(mailSender, mailAddress, customMailSubject, customMailBody);
    }

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

        javaMailSender.send(message);
    }

    @Override
    public Optional<MimeMessage[]> getMimeMessages() {
        return null;
    }

}
