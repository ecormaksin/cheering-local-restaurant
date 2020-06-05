package com.cheeringlocalrestaurant.infra.mail;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

//@Component
public class CustomMailServiceTestImpl implements CustomMailService {

    private GreenMail greenMail = new GreenMail();
    
    private MimeMessage[] mimeMessages = null;

    @Override
    // @formatter:off
    public void send(CustomMailSender mailSender, 
            MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            CustomMailBody customMailBody) {
    // @formatter:on
        greenMail.start();
        // @formatter:off
        GreenMailUtil.sendTextEmailTest(
                mailAddress.getValue(), 
                mailSender.toString(), 
                customMailSubject.getValue(), 
                customMailBody.getValue());
        // @formatter:on
        mimeMessages = greenMail.getReceivedMessages();
        greenMail.start();
    }

    @Override
    public Optional<MimeMessage[]> getMimeMessages() {
        return Optional.ofNullable(mimeMessages);
    }

}