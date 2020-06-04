package com.cheeringlocalrestaurant.infra.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;
import com.icegreen.greenmail.mail.MailAddress;
import com.icegreen.greenmail.util.GreenMailUtil;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CustomMailServiceTest {

    @Autowired
    CustomMailService customMailService;
    
    @Test
    void test() {
        try {
            final String senderNameStr = "差出人テスト";
            final String fromAddress = "from@localhost.com";
            final String toAddress = "to@localhost.com";
            final String subject = "some subject";
            final String body = "some body";
            CustomMailSender mailSender = new CustomMailSender(senderNameStr, fromAddress);
            // @formatter:off
            customMailService.send(mailSender, 
                    new MailAddress(toAddress), 
                    new CustomMailSubject(subject), 
                    new CustomMailBody(body));
            // @formatter:on
            Optional<MimeMessage[]> mimeMessagesOpt = customMailService.getMimeMessages();
            if (!mimeMessagesOpt.isPresent()) {
                assertTrue(true);
                return;
            }
            MimeMessage[] emails = mimeMessagesOpt.get();
            assertEquals(1, emails.length);
            MimeMessage email = emails[0];
            assertEquals(subject, email.getSubject());
            assertEquals(body, GreenMailUtil.getBody(email));
        } catch (Exception e) {
            fail(e);
        }
    }

}
