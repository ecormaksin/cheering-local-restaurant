package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.io.Serializable;

import javax.validation.constraints.*;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantName;
import com.cheeringlocalrestaurant.validation.notallblank.NotAllBlank;
import lombok.*;

@Data
@NoArgsConstructor
public class RestaurantTempRegisterForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotAllBlank(max = RestaurantName.MAX_SIZE)
	private String name;

	@NotBlank
	@Email
	private String mailAddress;

	@NotBlank
	@Pattern(regexp="checked")
	private String agreedTermOfUse;
}
