package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cheeringlocalrestaurant.domain.model.restaurant.MailAddress;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;

class RestaurantTempRegisterUseCaseTest {

	@Autowired
	private RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;
	
	@MockBean
	private RestaurantRepository restaurantRepository;
	
	@Test
	void _正常系() {
		final String name = "いろは食堂";
		final String email = "iroha@example.com";
		RestaurantTempRegister restaurantTempRegister = new RestaurantTempRegister(name, email);

		restaurantTempRegisterUseCase.execute(restaurantTempRegister);

		RestaurantAccount account = restaurantRepository.findByMailAddress(email);
		RestaurantId restaurantId = account.getRestaurantId();
		MailAddress mailAddress = account.getMailAddress();
		assertNotNull(restaurantId);
		assertNotNull(restaurantId.getValue());
	}

}
