package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTempRegistForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(min = 1, max = 50)
	private String name;
	
	@NotBlank
	@Email
	private String mailAddress;

	@AssertTrue
	private boolean agreedTermOfUse;
}
