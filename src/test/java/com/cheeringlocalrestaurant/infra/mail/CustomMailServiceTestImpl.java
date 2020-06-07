package com.cheeringlocalrestaurant.infra.mail;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

public class CustomMailServiceTestImpl implements CustomMailService {

    private GreenMail greenMail;

    private MimeMessage[] mimeMessages;

    private final CustomMailSender mailSender = new CustomMailSender("差出人テスト", "sender-test@example.com");
    
    @Override
    public CustomMailSender getDefaultMailSender() {
        return mailSender;
    }

    @Override
    // @formatter:off
    public void send(MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            CustomMailBody customMailBody) {
        send(mailSender, mailAddress, customMailSubject, customMailBody);
    }

    @Override
    // @formatter:off
    public void send(CustomMailSender mailSender, 
            MailAddress mailAddress, 
            CustomMailSubject customMailSubject,
            CustomMailBody customMailBody) {
    // @formatter:on
        greenMail = new GreenMail();
        greenMail.start();
        mimeMessages = null;
        // @formatter:off
        GreenMailUtil.sendTextEmailTest(
                mailAddress.getValue(), 
                mailSender.toString(), 
                customMailSubject.getValue(), 
                customMailBody.getValue());
        // @formatter:on
        mimeMessages = greenMail.getReceivedMessages();
        greenMail.stop();
    }

    @Override
    public Optional<MimeMessage[]> getMimeMessages() {
        return Optional.ofNullable(mimeMessages);
    }

}
