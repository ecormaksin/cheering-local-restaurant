package com.cheeringlocalrestaurant.infra.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;

import com.cheeringlocalrestaurant.CustomMailServiceTestConfig;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;
import com.icegreen.greenmail.util.GreenMailUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Import(CustomMailServiceTestConfig.class)
class SendMailTest {

    @Autowired
    private MockHttpServletRequest request;

    @Autowired
    private CustomMailService customMailService;
    
    @Autowired
    private FreeMarkerMailSender freeMarkerMailSender;

    @Value("${mail.sender.name}")
    private String mailSenderName;

    @Value("${mail.sender.address}")
    private String mailSenderAddress;

    @Value("${mail.test.to.address}")
    private String mailAddressTo;

    private CustomMailSender mailSender;

    @BeforeEach
    void setup() {
        mailSender = new CustomMailSender(mailSenderName, mailSenderAddress);
    }
    
    @Test
    void _すべて仮のパラメーターで送信する場合() {
        try {
            final String senderNameStr = "差出人テスト";
            final String fromAddress = "from@localhost.com";
            final String toAddress = "to@localhost.com";
            final String subject = "some subject";
            final String body = "some body";
            final CustomMailSender mailSender = new CustomMailSender(senderNameStr, fromAddress);
            // @formatter:off
            customMailService.send(mailSender, 
                    new MailAddress(toAddress), 
                    new CustomMailSubject(subject), 
                    new CustomMailBody(body));
            checkResult(customMailService.getMimeMessages(),
                    fromAddress, toAddress, subject, body); 
            // @formatter:on
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void _プロパティから読み込んだ場合のテスト() {
        try {
            final String subject = "送信テスト" + LocalDateTime.now().toString();
            final String body = "Test";
            // @formatter:off
            customMailService.send(mailSender, 
                    new MailAddress(mailAddressTo), 
                    new CustomMailSubject(subject), 
                    new CustomMailBody(body));
            checkResult(customMailService.getMimeMessages(),
                    mailSenderAddress, mailAddressTo, subject, body); 
            // @formatter:on
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void _プロパティから読み込みFreeMarkerで送信するテスト() throws Exception {

        final String subject = "ログインURLのお知らせ";
        final String templateName = "notify_login_url.ftl";

        Map<String, Object> model = new HashMap<>();
        final int remotePort = request.getRemotePort();
        final String remotePortString = (remotePort == 80 || remotePort == 443 ? "" : ":" + String.valueOf(remotePort));
        model.put("appRoot", request.getRequestURL() + remotePortString);
        model.put("token", UUID.randomUUID().toString());
        model.put("mailSenderName", mailSenderName);
        
        CustomMailBody customMailBody = freeMarkerMailSender.getProcessedBody(templateName, model);
        String body = customMailBody.getValue();
        Charset charset = StandardCharsets.UTF_8;
        byte[]encodedBytes = Base64.getEncoder()
                .encode(body.getBytes(charset));
        String encodedBody = new String(encodedBytes, charset);

        // @formatter:off
        freeMarkerMailSender.send(mailSender, 
                new MailAddress(mailAddressTo), 
                new CustomMailSubject(subject), 
                templateName, model);
        checkResult(freeMarkerMailSender.getMimeMessages(),
                mailSenderAddress, mailAddressTo, subject, encodedBody); 
        // @formatter:on
    }
    
    // @formatter:off
    private void checkResult(final Optional<MimeMessage[]> mimeMessagesOpt,
            String from, String to, String subject, String body) 
            throws Exception {
    // @formatter:on
        
        if (!mimeMessagesOpt.isPresent()) {
            log.info("MimeMessages don't exist.");
            assertTrue(true);
            return;
        }
        final MimeMessage[] emails = mimeMessagesOpt.get();
        assertEquals(1, emails.length);

        final MimeMessage email = emails[0];

        // 差出人
        final Address[] senders = email.getFrom();
        assertEquals(1, senders.length);
        final InternetAddress sender = (InternetAddress) senders[0];
        log.info(
                "class.typeName: " + sender.getClass().getTypeName()
                + "/ address: " + sender.getAddress()
                + "/ personal: " + sender.getPersonal()
                + "/ type: " + sender.getType()
                + "/ string: " + sender.toString()
                + "/ unicodeString: " + sender.toUnicodeString()
                );
        assertEquals(from, sender.getAddress());
        
        // 宛先
        final Address[] recipients = email.getAllRecipients();
        assertEquals(1, recipients.length);
        final InternetAddress recipient = (InternetAddress) recipients[0];
        assertEquals(to, recipient.getAddress());

        // 件名
        assertEquals(subject, email.getSubject());
        // 本文
        String bodyAct = GreenMailUtil.getBody(email);
        bodyAct = bodyAct.replaceAll("\r\n|\n", ""); // 返ってくる文字列が改行されており、比較時にエラーになってしまうので除去する
        assertEquals(body, bodyAct);
    }

}
