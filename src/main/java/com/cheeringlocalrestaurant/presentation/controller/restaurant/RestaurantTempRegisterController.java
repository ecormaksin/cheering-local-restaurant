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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(RestaurantTempRegisterController.PATH_BASE)
@RequiredArgsConstructor
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
	String register(HttpServletRequest request,
						@Validated RestaurantTempRegisterForm form, 
						BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			return VIEW_FORM;
		}
		try {
			restaurantTempRegisterUseCase.execute(null, request.getRemoteAddr());
		} catch(RestaurantMailAddressAlreadyRegisteredException e) {
			model.addAttribute("errorMessage",
					messagesource.getMessage("message.restaurant.mailAddressAlreadyRegistered", null, Locale.getDefault()));
			return VIEW_FORM;
		}
		redirectAttributes.addFlashAttribute(form);
		return "redirect:" + PATH_COMPLETED;
	}

	@GetMapping(PATH_REL_COMPLETED)
	String completed(RestaurantTempRegisterForm form) {
		return VIEW_COMPLETED;
	}
}
