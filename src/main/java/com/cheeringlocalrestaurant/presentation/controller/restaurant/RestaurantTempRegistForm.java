package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.io.Serializable;

import javax.validation.constraints.*;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantName;
import com.cheeringlocalrestaurant.validation.notallblank.NotAllBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTempRegistForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotAllBlank(min = RestaurantName.MIN_SIZE, max = RestaurantName.MAX_SIZE)
	private String name;
	
	@NotBlank
	@Email
	private String mailAddress;

	@AssertTrue
	private boolean agreedTermOfUse;
}
