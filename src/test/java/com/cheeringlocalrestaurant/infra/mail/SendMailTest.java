package com.cheeringlocalrestaurant.infra.mail;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.cheeringlocalrestaurant.CheeringLocalRestaurantApplication;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailBody;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSender;
import com.cheeringlocalrestaurant.domain.type.mail.CustomMailSubject;

import freemarker.template.Configuration;
import freemarker.template.Template;

@SpringBootTest(classes = CheeringLocalRestaurantApplication.class)
class SendMailTest {

    @Autowired
    MockHttpServletRequest request;

    @Autowired
    private CustomMailService customMailService;

    @Autowired
    private Configuration freemarkerConfig;

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
    @Disabled
    void test() {
        // @formatter:off
        customMailService.send(mailSender, 
                new MailAddress(mailAddressTo), 
                new CustomMailSubject("送信テスト" + LocalDateTime.now().toString()), 
                new CustomMailBody("Test"));
        // @formatter:on
    }

    @Test
    void sendByFreeMarkerTemplate() throws Exception {

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/mail_templates");

        Map<String, Object> model = new HashMap<>();
        final int remotePort = request.getRemotePort();
        final String remotePortString = (remotePort == 80 || remotePort == 443 ? "" : ":" + String.valueOf(remotePort));
        model.put("appRoot", request.getRequestURL() + remotePortString);
        model.put("token", UUID.randomUUID().toString());
        model.put("mailSenderName", mailSenderName);

        Template template = freemarkerConfig.getTemplate("notify_login_url.ftl");
        String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        // @formatter:off
        customMailService.send(mailSender, 
                new MailAddress(mailAddressTo), 
                new CustomMailSubject("ログインURLのお知らせ"), 
                new CustomMailBody(body));
        // @formatter:on
    }

}
