package com.cheeringlocalrestaurant.domain.type;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper=false)
public class MailAddress extends ValidationConcern {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Email
	private final String value;
	
	public MailAddress(final String value) {
    	this.value = value;

		this.validate(this);
	}
}
