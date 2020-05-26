package com.cheeringlocalrestaurant.domain.model.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestaurantNameTest {

	@Test
	void _nullの場合は例外が発生する() {
		assertThrows(IllegalArgumentException.class, () -> {new RestaurantName(null);});
	}

}
