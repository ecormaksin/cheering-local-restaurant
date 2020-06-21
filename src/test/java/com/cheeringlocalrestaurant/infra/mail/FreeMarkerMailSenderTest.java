package com.cheeringlocalrestaurant.infra.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;

import com.cheeringlocalrestaurant.CustomMailServiceTestConfig;
import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Import(CustomMailServiceTestConfig.class)
class FreeMarkerMailSenderTest {

    private final String mailAddressStr = TestDataUtils.MAIL_ADDRESS;
    private final MailAddress mailaddress = new MailAddress(mailAddressStr);

    @Autowired
    private FreeMarkerMailSender freeMarkerMailSender;

    @Autowired
    private MessageSource messagesource;

    @Value("${mail.sender.name}")
    private String mailSenderName;

    @Value("${restaurant.login.expiration.hours}")
    private int loginExpirationHours;

    @Test
    void _飲食店ログインURLのお知らせ() throws Exception {
        
        try {
            
            final String accessToken = UUID.randomUUID().toString();
            final String templateName = "notify_restaurant_login_url";
            final String appRoot = "http://" + TestDataUtils.REMOTE_IP_ADDRESS;
    
            Map<String, Object> model = new HashMap<>();
            model.put("appRoot", appRoot);
            model.put("token", accessToken);
            model.put("loginExpirationHours", loginExpirationHours);
            model.put("mailSenderName", mailSenderName);
    
            final String subject = messagesource.getMessage("restaurant.notify.login.url.mail.subject", null, Locale.getDefault());
    
            // @formatter:off
            freeMarkerMailSender.send(mailaddress, 
                    new CustomMailSubject(subject), 
                    templateName, model);
            // @formatter:on
            
            Optional<MimeMessage[]> mimeMessagesOpt = freeMarkerMailSender.getMimeMessages();
            final MimeMessage[] emails = mimeMessagesOpt.get();
            assertEquals(1, emails.length);
    
            final MimeMessage email = emails[0];
            final MimeMessageParser parser = new MimeMessageParser(email);
    
            // 宛先
            final List<Address> recipients = parser.getTo();
            assertEquals(1, recipients.size());
            final InternetAddress recipient = (InternetAddress) recipients.get(0);
            assertEquals(mailAddressStr, recipient.getAddress());
    
            // 件名
            assertEquals("ログインURLのお知らせ", parser.getSubject());
            // 本文
            String bodyAct = parser.parse().getPlainContent();
            log.info(bodyAct);
            assertTrue(bodyAct.contains("ログインするには、以下のURLからアクセスしてください。"));
            assertTrue(bodyAct.contains(appRoot + "/restaurant/login?accesstoken=" + accessToken));
            assertTrue(bodyAct.contains("URLの有効期間は24時間です。"));

        } catch (Exception e) {
            fail(e);
        }
    }

}
