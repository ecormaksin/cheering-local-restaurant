package com.cheeringlocalrestaurant.infra.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.cheeringlocalrestaurant.CustomMailServiceTestConfig;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Import(CustomMailServiceTestConfig.class)
class SendMailTest {

    @Autowired
    private CustomMailService customMailService;
    
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
        final MimeMessageParser parser = new MimeMessageParser(email);

        // 差出人
        assertEquals(from, parser.getFrom());
        
        // 宛先
        final List<Address> recipients = parser.getTo();
        assertEquals(1, recipients.size());
        final InternetAddress recipient = (InternetAddress) recipients.get(0);
        assertEquals(to, recipient.getAddress());

        // 件名
        assertEquals(subject, parser.getSubject());
        // 本文
        String bodyAct = parser.parse().getPlainContent();
        // 改行なしの本文でも、parser.parse().getPlainContent()で取得すると改行になっているので末尾の改行を除去する
        assertEquals(removeLastLineBreak(body), removeLastLineBreak(bodyAct)); 
    }

    private String removeLastLineBreak(final String value) {
        return value.replaceAll("(\r\n|\n)$", "");
    }

}
