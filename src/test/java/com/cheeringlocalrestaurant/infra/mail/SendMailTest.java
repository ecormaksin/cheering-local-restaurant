package com.cheeringlocalrestaurant.infra.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.cheeringlocalrestaurant.CheeringLocalRestaurantApplication;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;

@SpringBootTest(classes = CheeringLocalRestaurantApplication.class)
class SendMailTest {

    @Autowired
    MockHttpServletRequest request;
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
    private Configuration freemarkerConfig;
	
	@Value("${mail.sender.name}")
	private String mailSenderName;
	
	@Value("${mail.sender.address}")
	private String mailSenderAddress;
	
	private String mailSender;

	@Value("${mail.test.to.address}")
	private String mailAddressTo;
	
	@BeforeEach
	void setup() {
		mailSender = mailSenderName + "<" + mailSenderAddress + ">";
	}
	
	@Test
	@Disabled
	void test() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mailSender);
		message.setTo(mailAddressTo); 
		message.setSubject("送信テスト" + LocalDateTime.now().toString()); 
		message.setText("Test");
		emailSender.send(message);
	}
	
	@Test
	void sendByFreeMarkerTemplate() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/mail_templates");
		
		Map<String, Object> model = new HashMap<>();
		final int remotePort = request.getRemotePort();
		final String remotePortString = (remotePort == 80 || remotePort == 443 ? "" : ":" + String.valueOf(remotePort));
		model.put("appRoot", request.getRequestURL() + remotePortString);
		model.put("token", UUID.randomUUID().toString());
		model.put("mailSenderName", mailSenderName);

		Template t = freemarkerConfig.getTemplate("notify_login_url.ftl");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		
		message.setFrom(mailSender);
		message.setTo(mailAddressTo);
		message.setSubject("ログインURLのお知らせ");
		message.setText(text);
		
		emailSender.send(message);
	}

}
