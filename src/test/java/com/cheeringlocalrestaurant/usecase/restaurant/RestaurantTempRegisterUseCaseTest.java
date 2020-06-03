package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.account.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RestaurantTempRegisterUseCaseTest {

    @Autowired
    private RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;

    @MockBean
    private RestaurantRepository restaurantRepository;

    private final String name = "いろは食堂";
    private final String email = "iroha@example.com";
    private final String remoteIPAddr = "127.0.0.1";

    private RestaurantTempRegister tempRegister = new RestaurantTempRegister(name, email);

    @Test
    void _正常に登録できる場合はエラーが発生しないこと() {

        try {
            UserId userIdExp = new UserId(1L);
            RestaurantId restoIdExp = new RestaurantId(1L);
            // @formatter:off
            RestaurantAccount accountExp = RestaurantAccount.builder()
                                                .userId(userIdExp)
                                                .restaurantId(restoIdExp)
                                                .mailAddress(new MailAddress(email))
                                                .build();
            // @formatter:on
            given(restaurantRepository.doesExist(tempRegister.getMailAddress())).willReturn(false);
            given(restaurantRepository.save((RestaurantTempRegister) any(), (String) any())).willReturn(restoIdExp);
            given(restaurantRepository.findAccountById(restoIdExp)).willReturn(accountExp);

            RestaurantId restoIdAct = restaurantTempRegisterUseCase.execute(tempRegister, remoteIPAddr);
            assertNotNull(restoIdAct);
            assertNotNull(restoIdAct.getValue());

            RestaurantAccount accountAct = restaurantRepository.findAccountById(restoIdAct);
            UserId userIdAct = accountAct.getUserId();
            assertNotNull(userIdAct);
            assertEquals(userIdExp.getValue(), userIdAct.getValue());

            MailAddress mailAddress = accountAct.getMailAddress();
            assertNotNull(mailAddress);
            assertEquals(email, mailAddress.getValue());
        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    void _登録済みのメールアドレスの場合はエラーが発生すること() {
        
        RestaurantAccount account = new RestaurantAccount(1L, 1L, email);
        given(restaurantRepository.doesExist(account.getMailAddress())).willReturn(true);
        
        assertThrows(RestaurantMailAddressAlreadyRegisteredException.class, () -> {
            restaurantTempRegisterUseCase.execute(tempRegister, remoteIPAddr);
        });
    }

}
