package com.cheeringlocalrestaurant.infra.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class SendMailTest {

	@Autowired
	private JavaMailSender emailSender;
	
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

}
