package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(RestaurantLoginRequestController.PATH_BASE)
public class RestaurantLoginRequestController {

    static final String PATH_BASE = "/restaurant/login_request";
}
