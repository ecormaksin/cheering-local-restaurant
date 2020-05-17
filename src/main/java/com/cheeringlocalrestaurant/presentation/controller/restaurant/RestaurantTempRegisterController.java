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
@RequestMapping("restaurant/temp_register")
@RequiredArgsConstructor
@SessionAttributes(types = RestaurantTempRegisterForm.class)
public class RestaurantTempRegisterController {

	private final RestaurantTempRegisterUseCase restaurantTempRegisterUseCase;
	private final MessageSource messagesource;

	@GetMapping("/")
	String showForm(Model model) {
		model.addAttribute("restaurantTempRegisterForm", new RestaurantTempRegisterForm());
		return "restaurant/temp_register/form";
	}

	@PostMapping("/register")
	String register(@Validated RestaurantTempRegisterForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "restaurant/temp_register/form";
		}
		try {
			restaurantTempRegisterUseCase.execute(null);
		} catch(RestaurantMailAddressAlreadyRegisteredException e) {
			model.addAttribute("errorMessage",
					messagesource.getMessage("message.restaurant.mailAddressAlreadyRegistered", null, Locale.getDefault()));
			return "restaurant/temp_register/form";
		}
		return "redirect:/restaurant/temp_register/completed";
	}
}
