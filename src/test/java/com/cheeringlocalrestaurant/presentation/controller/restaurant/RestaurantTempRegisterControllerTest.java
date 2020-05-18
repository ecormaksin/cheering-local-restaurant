package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantMailAddressAlreadyRegisteredException;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantTempRegisterUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class RestaurantTempRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;
    @Mock
    private MessageSource messagesource;

    @Test
    void _店舗仮登録のルートへアクセスした時はフォームへ遷移する() throws Exception {
        this.mockMvc.perform(get("/" +  RestaurantTempRegisterController.PATH_BASE + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name(RestaurantTempRegisterController.VIEW_FORM));
    }

    @Test
    void _登録後は完了ページへ遷移する() throws Exception {
        given(restaurantTempRegisterUseCase.execute((RestaurantTempRegister) any())).willReturn(new RestaurantId());

        RestaurantTempRegisterForm form = RestaurantTempRegisterFormCreator.getOkPattern();
        this.mockMvc.perform(post(RestaurantTempRegisterController.PATH_REGISTER)
                    .param("name", form.getName())
                    .param("mailAddress", form.getMailAddress())
                    .param("agreed", form.getAgreedTermOfUse().toString())
                    .param("tranToken", form.getTranToken().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(RestaurantTempRegisterController.PATH_COMPLETED));
    }

    @Test
    void _入力エラーの場合はフォームへ戻る() throws Exception {
        this.mockMvc.perform(post(RestaurantTempRegisterController.PATH_REGISTER))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name(RestaurantTempRegisterController.VIEW_FORM));
    }

    @Test
    void _メールアドレスがすでに登録されている場合はフォームへ戻る() throws Exception {
        final String errorMessage = "メールアドレスはすでに登録されています。ログイン認証ページからアクセスしてください。";

        given(restaurantTempRegisterUseCase.execute((RestaurantTempRegister) any())).willThrow(RestaurantMailAddressAlreadyRegisteredException.class);
        doReturn(errorMessage).when(messagesource).getMessage("message.restaurant.mailAddressAlreadyRegistered", null, Locale.getDefault());

        RestaurantTempRegisterForm form = RestaurantTempRegisterFormCreator.getOkPattern();
        this.mockMvc.perform(post(RestaurantTempRegisterController.PATH_REGISTER)
                .param("name", form.getName())
                .param("mailAddress", form.getMailAddress())
                .param("agreed", form.getAgreedTermOfUse().toString())
                .param("tranToken", form.getTranToken().toString()))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("errorMessage", errorMessage))
                .andExpect(view().name(RestaurantTempRegisterController.VIEW_FORM));
    }
}
