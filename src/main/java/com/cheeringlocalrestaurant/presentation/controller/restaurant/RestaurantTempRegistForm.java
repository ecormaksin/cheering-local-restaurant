package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cheeringlocalrestaurant.presentation.controller.validation.PasswordMatches;
import com.cheeringlocalrestaurant.presentation.controller.validation.PasswordPair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class RestaurantTempRegistForm implements Serializable,PasswordPair {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	@Email
	private String mailAddress;

	@NotNull
	@NotEmpty
	private String password;

	@NotNull
	@NotEmpty
	private String matchingPassword;
}
