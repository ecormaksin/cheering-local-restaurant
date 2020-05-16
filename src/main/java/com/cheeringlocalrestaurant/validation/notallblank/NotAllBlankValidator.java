package com.cheeringlocalrestaurant.validation.notallblank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotAllBlankValidator implements ConstraintValidator<NotAllBlank, String> {

    private NotAllBlank constraintAnnotation;

    @Override
    public void initialize(NotAllBlank constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        final String replaced = value.replaceAll("[ 　]", "");
        final int length = replaced.length();
        if (length < constraintAnnotation.min() || constraintAnnotation.max() < length) return false;
        return true;
    }
}
