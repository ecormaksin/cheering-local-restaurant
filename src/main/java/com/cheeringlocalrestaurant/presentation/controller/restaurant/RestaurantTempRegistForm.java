package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.io.Serializable;

import javax.validation.constraints.*;

import com.cheeringlocalrestaurant.validation.notblank_withsize.NotBlankWithSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTempRegistForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlankWithSize(max = 50)
	private String name;
	
	@NotBlank
	@Email
	private String mailAddress;

	@AssertTrue
	private boolean agreedTermOfUse;
}
