package com.cheeringlocalrestaurant.validation.notblank_withsize;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankWithSizeValidator implements ConstraintValidator<NotBlankWithSize, String> {

    private NotBlankWithSize constraintAnnotation;

    @Override
    public void initialize(NotBlankWithSize constraintAnnotation) {
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
