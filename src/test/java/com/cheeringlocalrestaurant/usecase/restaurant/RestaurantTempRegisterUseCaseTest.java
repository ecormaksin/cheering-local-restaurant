package com.cheeringlocalrestaurant.usecase.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
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
    private final RemoteIpAddress remoteIpAddr = new RemoteIpAddress("127.0.0.1");

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
            given(restaurantRepository.findAccountByMailAddress(tempRegister.getMailAddress())).willReturn(Optional.ofNullable(null));
            given(restaurantRepository.save((RestaurantTempRegister) any(), (RemoteIpAddress) any())).willReturn(restoIdExp);
            given(restaurantRepository.getAccountById(restoIdExp)).willReturn(accountExp);

            RestaurantId restoIdAct = restaurantTempRegisterUseCase.execute(tempRegister, remoteIpAddr);
            assertNotNull(restoIdAct);
            assertNotNull(restoIdAct.getValue());

            RestaurantAccount accountAct = restaurantRepository.getAccountById(restoIdAct);
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
        given(restaurantRepository.findAccountByMailAddress(account.getMailAddress())).willReturn(Optional.ofNullable(account));
        
        assertThrows(RestaurantAccountAlreadyRegisteredException.class, () -> {
            restaurantTempRegisterUseCase.execute(tempRegister, remoteIpAddr);
        });
    }

}
