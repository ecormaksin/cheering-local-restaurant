package com.cheeringlocalrestaurant.domain.model.openhour;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OpenHourPartTest {

	@Test
	void _nullの場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, ()->{new StartTime(null);});
	}
	
	@Test
	void _長さ0の文字列の場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, ()->{new EndTime("");});
	}

	@Test
	void _正しい時刻の場合は引数に指定した文字列がそのまま返ってくる() {
		try {
			String testTimeString = "12:34";
			StartTime startTime = new StartTime(testTimeString);
			assertEquals(testTimeString, startTime.toString());
		} catch(Exception e) {
			fail();
		}
	}

	@Test
	void _24時の場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, ()->{new EndTime("24:00");});
	}

	@Test
	void _時刻でない場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, ()->{new StartTime("aa:aa");});
	}
}
