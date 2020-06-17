package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheeringlocalrestaurant.presentation.controller.BaseController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RestaurantLoginController {

    static final String PATH_BASE = "/restaurant/login";

    private final MessageSource messagesource;

    @GetMapping(PATH_BASE)
    String login(Model model, @RequestParam(value = "accesstoken") String accessToken) {
        model.addAttribute("restrantTopForm", new RestaurantTopForm());
        return RestaurantRegisteredController.VIEW_TOP;
    }
    
    @ExceptionHandler
    String moveToErrorPage(ServletRequestBindingException e, Model model) {
        log.error("", e);
        model.addAttribute("errorMessage", messagesource
                .getMessage("message.login.tokenNotIncluded", null, Locale.getDefault()));
        model.addAttribute("originalPageLink", RestaurantLoginRequestController.PATH_BASE);
        model.addAttribute("linkCaption", messagesource
                .getMessage("label.login.request", null, Locale.getDefault()));
        return BaseController.CUSTOM_ERROR_PAGE_PATH;
    }
}
