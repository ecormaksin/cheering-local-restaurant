package com.cheeringlocalrestaurant.domain.type.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestaurantIdTest {

	@Test
	void _正常な場合は例外が発生しない() {
		try {
			new RestaurantId(1L);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void _nullの場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, () -> {new RestaurantId(null);});
	}

	@Test
	void _0の場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, () -> {new RestaurantId(0L);});
	}

	@Test
	void _マイナスの場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, () -> {new RestaurantId(-1L);});
	}

	@Test
	void _最大値を超える場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, () -> {new RestaurantId(Long.MAX_VALUE + 1);});
	}
}
