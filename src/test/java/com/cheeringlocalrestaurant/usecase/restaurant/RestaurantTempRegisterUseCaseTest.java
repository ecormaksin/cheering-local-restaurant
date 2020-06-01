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
import com.cheeringlocalrestaurant.domain.type.UserId;
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

		UserId userIdExp = new UserId(1L);
		RestaurantId restoIdExp = new RestaurantId(1L);
		RestaurantAccount accountExp = RestaurantAccount.builder()
													.userId(userIdExp)
													.restaurantId(restoIdExp)
													.restaurantName(new RestaurantName(name))
													.mailAddress(new MailAddress(email))
													.build();
		given(restaurantRepository.save((RestaurantTempRegister) any(), (String) any())).willReturn(restoIdExp);
		given(restaurantRepository.findAccountById(restoIdExp)).willReturn(accountExp);
		
		RestaurantId restoIdAct = restaurantTempRegisterUseCase.execute(tempRegister, "127.0.0.1");
		assertNotNull(restoIdAct);
		assertNotNull(restoIdAct.getValue());

		RestaurantAccount accountAct = restaurantRepository.findAccountById(restoIdAct);
		UserId userIdAct = accountAct.getUserId();
		assertNotNull(userIdAct);
		assertEquals(userIdExp.getValue(), userIdAct.getValue());
		
		RestaurantName restoNameAct = accountAct.getRestaurantName();
		assertNotNull(restoNameAct);
		assertEquals(name, restoNameAct.getValue());

		MailAddress mailAddress = accountAct.getMailAddress();
		assertNotNull(mailAddress);
		assertEquals(email, mailAddress.getValue());
	}

}
