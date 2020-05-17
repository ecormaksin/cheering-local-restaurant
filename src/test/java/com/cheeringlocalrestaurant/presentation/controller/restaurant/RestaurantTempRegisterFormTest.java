package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import org.apache.commons.lang3.StringUtils;
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

public class RestaurantTempRegisterFormTest {

    private Validator validator;

    private String nameNotAllBlankMessage = "{0}'s size must be between 1 and 50. Only blanks are not allowed.";

    private RestaurantTempRegisterForm form = new RestaurantTempRegisterForm();

    @BeforeEach
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        RestaurantTempRegisterFormCreator.setOkPattern(form);
    }

    private Set<ConstraintViolation<RestaurantTempRegisterForm>> validate() {
        return validator.validate(form);
    }

    private void validateNameOnly() {
        validateSingleItem(nameNotAllBlankMessage);
    }

    private void validateSingleItem(final String expectedMessage) {
        Set<ConstraintViolation<RestaurantTempRegisterForm>> violations = validate();
        assertTrue(violations.size() == 1);
        List<ConstraintViolation<RestaurantTempRegisterForm>> violationList = new ArrayList<>(violations);
        ConstraintViolation<RestaurantTempRegisterForm> violation = violationList.get(0);
        assertEquals(expectedMessage, violation.getMessage());
    }

    @Test
    void _正常ケース() throws Exception {
        Set<ConstraintViolation<RestaurantTempRegisterForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    void _店名が長さ0の文字列はNG() throws Exception {
        form.setName("");
        validateNameOnly();
    }

    @Test
    void _店名が50字を超える場合はNG() throws Exception {
        form.setName(StringUtils.repeat("あ", 51));
        validateNameOnly();
    }

    @Test
    void _店名がスペースとタブだけの場合はNG() throws Exception {
        form.setName(" 　\t");
        validateNameOnly();
    }

    @Test
    void _メールアドレスが長さ0の文字列の場合はNG() throws Exception {
        form.setMailAddress("");
        validateSingleItem("must not be blank");
    }

    @Test
    void _メールアドレスが不正の場合はNG() throws Exception {
        form.setMailAddress("iroha");
        validateSingleItem("must be a well-formed email address");
    }

    @Test
    void _利用規約が未チェックの場合はNG() throws Exception {
        form.setAgreedTermOfUse(false);
        validateSingleItem("must be true");
    }

    @Test
    void _すべて未入力の場合はNG() throws Exception {
        form.setName("");
        form.setMailAddress("");
        form.setAgreedTermOfUse(false);
        Set<ConstraintViolation<RestaurantTempRegisterForm>> violations = validate();
        assertTrue(violations.size() == 3);
    }
}
