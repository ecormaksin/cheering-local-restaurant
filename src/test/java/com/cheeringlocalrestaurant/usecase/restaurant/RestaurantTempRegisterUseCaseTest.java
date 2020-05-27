package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cheeringlocalrestaurant.domain.model.restaurant.Restaurant;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RestaurantTempRegisterUseCaseTest {

	@Autowired
	private RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;
	
	@MockBean
	private RestaurantRepository restaurantRepository;
	
	@Test
	void _正常系() {
		final String name = "いろは食堂";
		final String email = "iroha@example.com";

		RestaurantTempRegister tempRegister = new RestaurantTempRegister(name, email);

		RestaurantId idExpected = new RestaurantId(1L);
		RestaurantAccount accountExpected = RestaurantAccount.builder()
													.id(idExpected)
													.name(new RestaurantName(name))
													.mailAddress(new MailAddress(email))
													.build();
		given(restaurantRepository.save((RestaurantTempRegister) any(), (String) any())).willReturn(idExpected);
		given(restaurantRepository.findAccountById(idExpected)).willReturn(accountExpected);
		
		RestaurantId idActual = restaurantTempRegisterUseCase.execute(tempRegister, "127.0.0.1");
		assertNotNull(idActual);
		assertNotNull(idActual.getValue());

		RestaurantAccount accountActual = restaurantRepository.findAccountById(idActual);
		RestaurantName nameActual = accountActual.getName();
		MailAddress mailAddress = accountActual.getMailAddress();
		assertNotNull(nameActual);
		assertEquals(name, nameActual.getValue());
		assertNotNull(mailAddress);
		assertEquals(email, mailAddress.getValue());
	}

}
