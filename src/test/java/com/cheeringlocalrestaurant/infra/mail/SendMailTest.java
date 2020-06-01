package com.cheeringlocalrestaurant.infra.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.cheeringlocalrestaurant.CheeringLocalRestaurantApplication;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;

@SpringBootTest(classes = CheeringLocalRestaurantApplication.class,
	webEnvironment = WebEnvironment.NONE)
class SendMailTest {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
    private Configuration freemarkerConfig;
	
	@Value("${mail.from.address}")
	private String mailAddressFrom;
	
	@Value("${mail.test.to.address}")
	private String mailAddressTo;
	
	@Test
	void test() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mailAddressFrom);
		message.setTo(mailAddressTo); 
		message.setSubject("送信テスト" + LocalDateTime.now().toString()); 
		message.setText("Test");
		emailSender.send(message);
	}
	
	@Data
	class TestModel {
		private String subject = "ログインURLのお知らせ";
		private String toAddress;
		private String token = UUID.randomUUID().toString();
	}
	
	@Test
	void sendByFreeMarkerTemplate() throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/mail_templates");
		
		TestModel testModel = new TestModel();
		testModel.setToAddress(mailAddressTo);

		Template t = freemarkerConfig.getTemplate("notify_login_url.ftl");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, null);
		
		helper.setFrom(mailAddressFrom);
		helper.setTo(testModel.getToAddress());
		helper.setText(text, true);
		helper.setSubject("");
		
		emailSender.send(message);
	}

}
