package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestaurantLoginController {

    static final String PATH_BASE = "restaurant/login";

    @GetMapping("/" + PATH_BASE)
    String login(Model model) {
        model.addAttribute("restrantTopForm", new RestaurantTopForm());
        return RestaurantRegisteredController.VIEW_TOP;
    }
}
