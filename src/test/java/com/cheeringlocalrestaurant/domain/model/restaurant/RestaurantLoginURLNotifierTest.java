package com.cheeringlocalrestaurant.domain.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;

import com.cheeringlocalrestaurant.CustomMailServiceTestConfig;
import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationHours;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;
import com.cheeringlocalrestaurant.infra.mail.FreeMarkerMailSender;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Import(CustomMailServiceTestConfig.class)
class RestaurantLoginURLNotifierTest {

    @Autowired
    private MockHttpServletRequest request;

    @Autowired
    private RestaurantLoginURLNotifier restaurantLoginURLNotifier;
    
    @MockBean
    private FreeMarkerMailSender freeMarkerMailSender;
    
    @Value("${restaurant.login.expiration.hours}")
    private int loginExpirationHours;

    private static final String mailAddressStr = TestDataUtils.MAIL_ADDRESS;
    private static final MailAddress mailAddress = TestDataUtils.mailAddress;

    @Test
    void _正常系() {
        try {
            final AccessToken accessToken = new AccessToken();
            final AccessTokenExpirationHours expirationHours = new AccessTokenExpirationHours(loginExpirationHours);
            restaurantLoginURLNotifier.execute(request, mailAddress, accessToken, expirationHours);
            
            Optional<MimeMessage[]> mimeMessagesOpt = restaurantLoginURLNotifier.getMimeMessages();
            if (!mimeMessagesOpt.isPresent()) {
                log.info("MimeMessages don't exist.");
                assertTrue(true);
                return;
            }
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
            assertTrue(bodyAct.contains("/login?" + accessToken.getValue()));
            assertTrue(bodyAct.contains("URLの有効期間は" + loginExpirationHours + "時間です。"));
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void _異常系() throws Exception{

        doThrow(new IOException()).when(freeMarkerMailSender).send((MailAddress) any(), (CustomMailSubject) any(), (String) any(),any());

        assertThrows(RestaurantLoginURLNotifyFailedException.class, () -> {
            final AccessToken accessToken = new AccessToken();
            final AccessTokenExpirationHours expirationHours = new AccessTokenExpirationHours(loginExpirationHours);
            restaurantLoginURLNotifier.execute(request, mailAddress, accessToken, expirationHours);
        });
    }

}
