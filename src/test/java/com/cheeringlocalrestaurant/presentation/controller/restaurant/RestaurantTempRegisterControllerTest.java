package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantLoginURLNotifyFailedException;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.login.LoginRequestId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.presentation.controller.BaseController;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantAccountAlreadyRegisteredException;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantNotifyLoginUrlUseCase;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantTempRegisterUseCase;

@WebMvcTest(RestaurantTempRegisterController.class)
public class RestaurantTempRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;
    @MockBean
    private RestaurantNotifyLoginUrlUseCase restaurantNotifyLoginUrlUseCase;
    @MockBean
    private MessageSource messagesource;

    @Test
    void _店舗仮登録のルートへアクセスした時はフォームへ遷移する() throws Exception {
        this.mockMvc.perform(get(RestaurantTempRegisterController.PATH_BASE + "/")).andExpect(status().isOk())
                .andExpect(view().name(RestaurantTempRegisterController.VIEW_FORM));
    }

    @Test
    void _登録後は完了ページへ遷移する() throws Exception {
        given(restaurantTempRegisterUseCase.execute((RestaurantTempRegister) any(), (RemoteIpAddress) any()))
                .willReturn(new RestaurantId(1L));
        given(restaurantNotifyLoginUrlUseCase.execute((HttpServletRequest) any(), (MailAddress) any(), (RemoteIpAddress) any())).willReturn(new LoginRequestId(1L));

        final RestaurantTempRegisterForm form = RestaurantTempRegisterFormCreator.getOkPattern();
        this.mockMvc
                .perform(post(RestaurantTempRegisterController.PATH_REGISTER).param("name", form.getName())
                        .param("mailAddress", form.getMailAddress())
                        .param("agreedTermOfUse", form.getAgreedTermOfUse()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(RestaurantTempRegisterController.PATH_COMPLETED + "*"));
    }

    @Test
    void _完了ページ遷移時はメッセージが表示される() throws Exception {
        final RestaurantTempRegisterForm form = RestaurantTempRegisterFormCreator.getOkPattern();
        final String key = "restaurantTempRegisterForm";

        this.mockMvc.perform(get(RestaurantTempRegisterController.PATH_COMPLETED).flashAttr(key, form))
                .andExpect(status().isOk()).andExpect(model().attribute(key, form))
                .andExpect(view().name(RestaurantTempRegisterController.VIEW_COMPLETED));
    }

    @Test
    void _入力エラーの場合はフォームへ戻る() throws Exception {
        this.mockMvc.perform(post(RestaurantTempRegisterController.PATH_REGISTER)).andExpect(status().isOk())
                .andExpect(model().hasErrors()).andExpect(view().name(RestaurantTempRegisterController.VIEW_FORM));
    }

    @Test
    void _メールアドレスがすでに登録されている場合はフォームへ戻る() throws Exception {
        final String errorMessage = "メールアドレスはすでに登録されています。ログイン認証ページからアクセスしてください。";

        given(restaurantTempRegisterUseCase.execute((RestaurantTempRegister) any(), (RemoteIpAddress) any()))
                .willThrow(RestaurantAccountAlreadyRegisteredException.class);
        doReturn(errorMessage).when(messagesource).getMessage("message.restaurant.mailAddressAlreadyRegistered", null,
                Locale.getDefault());

        RestaurantTempRegisterForm form = RestaurantTempRegisterFormCreator.getOkPattern();
        this.mockMvc
                .perform(post(RestaurantTempRegisterController.PATH_REGISTER).param("name", form.getName())
                        .param("mailAddress", form.getMailAddress())
                        .param("agreedTermOfUse", form.getAgreedTermOfUse()))
                .andExpect(status().isOk()).andExpect(model().hasNoErrors())
                .andExpect(model().attribute("errorMessage", errorMessage))
                .andExpect(view().name(RestaurantTempRegisterController.VIEW_FORM));
    }

    @Test
    void _ログインURLの通知処理に失敗した場合は共通のエラーページへ遷移する() throws Exception {
        final String errorMessage = "飲食店の仮登録に失敗しました。\n最初からやり直してください。";
        doReturn(errorMessage).when(messagesource).getMessage("message.restaurant.tempRegisterFailed", null,
                Locale.getDefault());

        given(restaurantTempRegisterUseCase.execute((RestaurantTempRegister) any(), (RemoteIpAddress) any()))
                .willReturn(new RestaurantId(1L));
        given(restaurantNotifyLoginUrlUseCase.execute((HttpServletRequest) any(), (MailAddress) any(), (RemoteIpAddress) any())).willThrow(RestaurantLoginURLNotifyFailedException.class);

        final RestaurantTempRegisterForm form = RestaurantTempRegisterFormCreator.getOkPattern();
        this.mockMvc
                .perform(post(RestaurantTempRegisterController.PATH_REGISTER).param("name", form.getName())
                        .param("mailAddress", form.getMailAddress())
                        .param("agreedTermOfUse", form.getAgreedTermOfUse()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMessage", errorMessage))
                .andExpect(model().attribute("originalPageLink", RestaurantTempRegisterController.PATH_BASE))
                .andExpect(view().name(BaseController.CUSTOM_ERROR_PAGE_PATH));
    }

}
