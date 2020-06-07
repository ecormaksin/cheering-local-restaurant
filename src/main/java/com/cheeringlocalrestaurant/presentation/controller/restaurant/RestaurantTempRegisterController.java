package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantLoginURLNotifyFailedException;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.presentation.controller.BaseController;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantAccountAlreadyRegisteredException;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantNotifyLoginUrlUseCase;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantTempRegisterUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(RestaurantTempRegisterController.PATH_BASE)
@RequiredArgsConstructor
@Slf4j
public class RestaurantTempRegisterController {

    static final String PATH_BASE = "restaurant/temp_register";

    static final String VIEW_FORM = PATH_BASE + "/form";

    static final String PATH_REL_REGISTER = "/register";
    static final String PATH_REGISTER = "/" + PATH_BASE + PATH_REL_REGISTER;

    static final String PATH_REL_COMPLETED = "/completed";
    static final String VIEW_COMPLETED = PATH_BASE + PATH_REL_COMPLETED;
    static final String PATH_COMPLETED = "/" + VIEW_COMPLETED;

    private final RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;
    private final RestaurantNotifyLoginUrlUseCase restaurantNotifyLoginUrlUseCase;
    private final MessageSource messagesource;

    @GetMapping("/")
    String showForm(Model model) {
        model.addAttribute("restaurantTempRegisterForm", new RestaurantTempRegisterForm());
        return VIEW_FORM;
    }

    @PostMapping(PATH_REL_REGISTER)
    String register(HttpServletRequest request, @Validated RestaurantTempRegisterForm form, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return VIEW_FORM;
        }
        try {
            final String email = form.getMailAddress();
            RestaurantTempRegister tempRegister = new RestaurantTempRegister(form.getName(), email);
            RemoteIpAddress remoteIpAddress = new RemoteIpAddress(request.getRemoteAddr());
            restaurantTempRegisterUseCase.execute(tempRegister, remoteIpAddress);
            restaurantNotifyLoginUrlUseCase.execute(request, new MailAddress(email), remoteIpAddress);
        } catch (RestaurantAccountAlreadyRegisteredException e) {
            model.addAttribute("errorMessage", messagesource
                    .getMessage("message.restaurant.mailAddressAlreadyRegistered", null, Locale.getDefault()));
            return VIEW_FORM;
        } catch (RestaurantLoginURLNotifyFailedException e) {
            log.error("", e);
            model.addAttribute("errorMessage", messagesource
                    .getMessage("message.restaurant.tempRegisterFailed", null, Locale.getDefault()));
            model.addAttribute("originalPageLink", PATH_BASE);
            return BaseController.CUSTOM_ERROR_PAGE_PATH;
        }

        redirectAttributes.addFlashAttribute(form);
        return "redirect:" + PATH_COMPLETED;
    }

    @GetMapping(PATH_REL_COMPLETED)
    String completed(RestaurantTempRegisterForm form) {
        return VIEW_COMPLETED;
    }
}
