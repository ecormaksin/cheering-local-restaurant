package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantMailAddressAlreadyRegisteredException;
import com.cheeringlocalrestaurant.usecase.restaurant.RestaurantTempRegisterUseCase;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Controller
@RequestMapping(RestaurantTempRegisterController.PATH_BASE)
@RequiredArgsConstructor
@SessionAttributes(types = RestaurantTempRegisterForm.class)
public class RestaurantTempRegisterController {

	static final String PATH_BASE = "restaurant/temp_register";

	static final String VIEW_FORM = PATH_BASE + "/form";

	static final String PATH_REL_REGISTER = "/register";
	static final String PATH_REGISTER = "/" + PATH_BASE + PATH_REL_REGISTER;

	static final String PATH_REL_COMPLETED = "/completed";
	static final String VIEW_COMPLETED = PATH_BASE + PATH_REL_COMPLETED;
	static final String PATH_COMPLETED = "/" + VIEW_COMPLETED;

	private final RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;
	private final MessageSource messagesource;

	@GetMapping("/")
	String showForm(Model model) {
		model.addAttribute("restaurantTempRegisterForm", new RestaurantTempRegisterForm());
		return VIEW_FORM;
	}

	@PostMapping(PATH_REL_REGISTER)
	String register(@Validated RestaurantTempRegisterForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return VIEW_FORM;
		}
		try {
			restaurantTempRegisterUseCase.execute(null);
		} catch(RestaurantMailAddressAlreadyRegisteredException e) {
			model.addAttribute("errorMessage",
					messagesource.getMessage("message.restaurant.mailAddressAlreadyRegistered", null, Locale.getDefault()));
			return VIEW_FORM;
		}
		return "redirect:" + PATH_COMPLETED;
	}
}
