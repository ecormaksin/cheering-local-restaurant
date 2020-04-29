package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("restaurant/temp_regist")
@RequiredArgsConstructor
public class RestaurantTempRegistController {

	@ModelAttribute
	RestaurantTempRegistForm setupForm() {
		return new RestaurantTempRegistForm();
	}
	
	@GetMapping
	String showForm(Model model) {
		model.addAttribute("restaurantTempRegistForm", setupForm());
		return "restaurant/temp_regist/form";
	}
}
