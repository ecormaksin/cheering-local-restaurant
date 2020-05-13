package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestaurantTempRegistFormTest {

    private Validator validator;
    private RestaurantTempRegistForm form = new RestaurantTempRegistForm();

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        form.setName("いろは食堂");
        form.setMailAddress("iroha@example.com");
        form.setAgreedTermOfUse(true);
    }

    @Test
    void _正常ケース() throws Exception {
        Set<ConstraintViolation<RestaurantTempRegistForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }
}
