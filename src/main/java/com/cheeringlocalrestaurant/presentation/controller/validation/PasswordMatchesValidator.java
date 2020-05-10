package com.cheeringlocalrestaurant.presentation.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
		final PasswordPair passwordPair = (PasswordPair) obj;
		return passwordPair.getPassword().equals(passwordPair.getMatchingPassword());
	}
}
