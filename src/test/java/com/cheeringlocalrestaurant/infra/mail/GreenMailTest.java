package com.cheeringlocalrestaurant.infra.mail;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

class GreenMailTest {

	GreenMail greenMail = new GreenMail();
	
	@BeforeEach
	void setUp() {
		greenMail.start();
	}
	
	@Test
	void test() {
		GreenMailUtil.sendTextEmailTest("to@localhost.com", "from@localhost.com", "some subject", "some body");
		assertEquals("some body", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	}
	
	@AfterEach
	void tearDown() {
		greenMail.stop();
	}

}
