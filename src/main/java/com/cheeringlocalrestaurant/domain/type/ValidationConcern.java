package com.cheeringlocalrestaurant.domain.type;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public abstract class ValidationConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    protected <T> void validate(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (violations.size() == 0) return;

        StringBuilder sb = new StringBuilder();
        boolean firstLine = true;
        for (ConstraintViolation<T> violation : violations) {
            if (!firstLine)
                sb.append(System.lineSeparator());
            sb.append(violation.getMessage());
            firstLine = false;
        }
        throw new IllegalArgumentException(sb.toString());
    }
}
