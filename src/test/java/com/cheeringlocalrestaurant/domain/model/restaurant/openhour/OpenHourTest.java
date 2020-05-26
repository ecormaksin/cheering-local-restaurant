package com.cheeringlocalrestaurant.domain.model.restaurant.openhour;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.cheeringlocalrestaurant.domain.model.restaurant.openhour.OpenHour;

class OpenHourTest {
	
	private static final String GENERAL_START_TIME = "11:00";
	private static final String GENERAL_END_TIME = "18:00";

	@Test
	void _正常な開始時刻と終了時刻の場合は例外が発生しない() {
		String start = GENERAL_START_TIME;
		String end = GENERAL_END_TIME;
		OpenHour openHour = new OpenHour(start, end);
		assertEquals(start + "〜" + end, openHour.toString());
	}
	
	@Test
	void _開始時刻と終了時刻を逆に指定していた場合は昇順に並び替える() {
		String start = GENERAL_END_TIME;
		String end = GENERAL_START_TIME;
		OpenHour openHour = new OpenHour(start, end);
		assertEquals(end + "〜" + start, openHour.toString());
	}
	
	@Test
	void _開始と終了が同じ時刻の場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, ()->{new OpenHour(GENERAL_START_TIME, GENERAL_START_TIME);});
	}

	@Test
	void _開始時刻のみ指定していた場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, ()->{new OpenHour(GENERAL_START_TIME, "");});
	}

	@Test
	void _終了時刻のみ指定していた場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, ()->{new OpenHour("", GENERAL_END_TIME);});
	}
	
	@Test
	void _時間帯が被っていない() {
		OpenHour openHour1 = new OpenHour("13:00", "14:00");
		OpenHour openHour2 = new OpenHour("15:00", "16:00");
		
		assertFalse(openHour1.isOverlapped(openHour2));
	}
	
	@Test
	void _時間帯が被っている_開始と終了() {
		OpenHour openHour1 = new OpenHour("13:00", "14:00");
		OpenHour openHour2 = new OpenHour("12:00", "13:01");
		
		assertTrue(openHour1.isOverlapped(openHour2));
	}

	@Test
	void _時間帯が被っている_同一() {
		OpenHour openHour1 = new OpenHour("13:00", "14:00");
		OpenHour openHour2 = new OpenHour("13:00", "14:00");
		
		assertTrue(openHour1.isOverlapped(openHour2));
	}

	@Test
	void _時間帯が被っている_内包() {
		OpenHour openHour1 = new OpenHour("13:00", "14:00");
		OpenHour openHour2 = new OpenHour("13:01", "13:59");
		
		assertTrue(openHour1.isOverlapped(openHour2));
	}
}
