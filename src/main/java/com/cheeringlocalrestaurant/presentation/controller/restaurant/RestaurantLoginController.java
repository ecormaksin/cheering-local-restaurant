package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenExpiredException;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenNotFoundException;
import com.cheeringlocalrestaurant.domain.model.login_request.LoginAccessTokenUpdatedException;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.presentation.controller.BaseController;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantLoginUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RestaurantLoginController {

    static final String PATH_BASE = "/restaurant/login";

    private final RestaurantLoginUseCase restaurantLoginUseCase;
    private final MessageSource messagesource;

    @Value("${mail.sender.address}")
    private String mailSenderAddress;

    @GetMapping(PATH_BASE)
    String login(Model model, @RequestParam(value = "accesstoken") String accessToken) {

        try {

            restaurantLoginUseCase.execute(new AccessToken(accessToken));
            model.addAttribute("restrantTopForm", new RestaurantTopForm());
            return RestaurantRegisteredController.VIEW_TOP;
        } catch (LoginAccessTokenNotFoundException e) {

            return moveToLoginRequestPageWhenValidTokenNotIncluded(e, model);
        } catch (LoginAccessTokenExpiredException | LoginAccessTokenUpdatedException e) {

            String errorMessage = null;
            if(e instanceof LoginAccessTokenExpiredException) {
                errorMessage = messagesource.getMessage("message.login.tokenExpired", null, Locale.getDefault());
            }
            if (e instanceof LoginAccessTokenUpdatedException) {
                errorMessage = messagesource.getMessage("message.login.tokenUpdated", new Object[]{mailSenderAddress}, Locale.getDefault());
            }
            return moveToLoginRequestPage(e, errorMessage, model);
        }
    }
    
    @ExceptionHandler
    String moveToErrorPage(ServletRequestBindingException e, Model model) {
        return moveToLoginRequestPageWhenValidTokenNotIncluded(e, model);
    }
    
    private String moveToLoginRequestPageWhenValidTokenNotIncluded(Throwable e, Model model) {
        final String errorMessage = messagesource.getMessage("message.login.validTokenNotIncluded", null, Locale.getDefault());
        return moveToLoginRequestPage(e, errorMessage, model);
    }
    
    private String moveToLoginRequestPage(final Throwable e, final String errorMessage, Model model) {

        log.error("", e);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("originalPageLink", RestaurantLoginRequestController.PATH_BASE);
        model.addAttribute("linkCaption", messagesource.getMessage("label.login.request", null, Locale.getDefault()));

        return BaseController.CUSTOM_ERROR_PAGE_PATH;
    }
}
