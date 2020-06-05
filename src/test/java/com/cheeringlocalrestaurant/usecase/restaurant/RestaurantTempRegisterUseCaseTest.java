package com.cheeringlocalrestaurant.usecase.restaurant;

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

import com.cheeringlocalrestaurant.TestDataUtils;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RestaurantTempRegisterUseCaseTest {

    @Autowired
    private RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;

    @MockBean
    private RestaurantRepository restaurantRepository;

    private static final String mailAddressStr = TestDataUtils.MAIL_ADDRESS;
    private static final MailAddress mailAddress = TestDataUtils.mailAddress;
    private static final RemoteIpAddress remoteIpAddress = TestDataUtils.remoteIpAddress;
    private static final RestaurantTempRegister restaurantTempRegister = TestDataUtils.restaurantTempRegister;

    @Test
    void _メールアドレスが未登録の場合は正常に登録できること() {

        try {
            RestaurantId restoIdExp = new RestaurantId(1L);
            given(restaurantRepository.findAccountByMailAddress(mailAddress)).willReturn(Optional.ofNullable(null));
            given(restaurantRepository.save((RestaurantTempRegister) any(), (RemoteIpAddress) any())).willReturn(restoIdExp);

            RestaurantId restoIdAct = restaurantTempRegisterUseCase.execute(restaurantTempRegister, remoteIpAddress);
            assertNotNull(restoIdAct);
            assertNotNull(restoIdAct.getValue());

        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    void _メールアドレスが登録済の場合は例外が発生すること() {
        
        RestaurantAccount account = new RestaurantAccount(1L, 1L, mailAddressStr);
        given(restaurantRepository.findAccountByMailAddress(account.getMailAddress())).willReturn(Optional.ofNullable(account));
        
        assertThrows(RestaurantAccountAlreadyRegisteredException.class, () -> {
            restaurantTempRegisterUseCase.execute(restaurantTempRegister, remoteIpAddress);
        });
    }

}
