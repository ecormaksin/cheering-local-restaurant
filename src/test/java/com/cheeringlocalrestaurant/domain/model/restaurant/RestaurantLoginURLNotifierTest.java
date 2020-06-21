package com.cheeringlocalrestaurant.domain.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.SystemBaseURL;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationHours;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;
import com.cheeringlocalrestaurant.infra.mail.FreeMarkerMailSender;

@SpringBootTest
class RestaurantLoginURLNotifierTest {

    @Autowired
    private RestaurantLoginURLNotifier restaurantLoginURLNotifier;
    
    @MockBean
    private FreeMarkerMailSender freeMarkerMailSender;
    
    @Value("${restaurant.login.expiration.hours}")
    private int loginExpirationHours;

    private static final SystemBaseURL systemBaseURL = TestDataUtils.systemBaseURL;
    private static final MailAddress mailAddress = TestDataUtils.mailAddress;

    @Test
    void _正常系() {
        try {
            final AccessToken accessToken = new AccessToken();
            final AccessTokenExpirationHours expirationHours = new AccessTokenExpirationHours(loginExpirationHours);
            restaurantLoginURLNotifier.execute(systemBaseURL, mailAddress, accessToken, expirationHours);
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
            restaurantLoginURLNotifier.execute(systemBaseURL, mailAddress, accessToken, expirationHours);
        });
    }

}
