package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTempRegistFormTest {

    private Validator validator;

    private String nameNotAllBlankMessage = "{0}'s size must be between 1 and 50. Only blanks are not allowed.";

    private RestaurantTempRegistForm form = new RestaurantTempRegistForm();

    @BeforeEach
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        form.setName("いろは食堂");
        form.setMailAddress("iroha@example.com");
        form.setAgreedTermOfUse(true);
    }

    private void validateNameOnly() {
        Set<ConstraintViolation<RestaurantTempRegistForm>> violations = validator.validate(form);
        assertTrue(violations.size() == 1);
        List<ConstraintViolation<RestaurantTempRegistForm>> violationList = new ArrayList<>(violations);
        ConstraintViolation<RestaurantTempRegistForm> violation = violationList.get(0);
        assertEquals(nameNotAllBlankMessage, violation.getMessage());
    }

    @Test
    void _正常ケース() throws Exception {
        Set<ConstraintViolation<RestaurantTempRegistForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    void _店名がnullはNG() throws Exception {
        form.setName(null);
        validateNameOnly();
    }

    @Test
    void _店名が長さ0の文字列はNG() throws Exception {
        form.setName("");
        validateNameOnly();
    }

    @Test
    void _店名が半角スペース1文字はNG() throws Exception {
        form.setName(" ");
        validateNameOnly();
    }

    @Test
    void _店名が全角スペース1文字はNG() throws Exception {
        form.setName("　");
        validateNameOnly();
    }

    @Test
    void _店名がスペースだけの場合はNG() throws Exception {
        form.setName("   　　　");
        validateNameOnly();
    }
}
