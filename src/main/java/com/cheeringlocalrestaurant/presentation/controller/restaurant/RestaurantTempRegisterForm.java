package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.*;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantName;
import com.cheeringlocalrestaurant.validation.notallblank.NotAllBlank;
import lombok.*;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RestaurantTempRegisterForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@NotAllBlank(max = RestaurantName.MAX_SIZE)
	private String name;

	@Getter
	@Setter
	@NotBlank
	@Email
	private String mailAddress;

	@Getter
	@Setter
	@AssertTrue
	private Boolean agreedTermOfUse;

	@Getter
	@NotNull
	private UUID tranToken = UUID.randomUUID();
}
