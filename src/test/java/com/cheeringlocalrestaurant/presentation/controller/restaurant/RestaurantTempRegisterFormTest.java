package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTempRegisterFormTest {

    private Validator validator;

    private RestaurantTempRegisterForm form = new RestaurantTempRegisterForm();

    @BeforeEach
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        RestaurantTempRegisterFormCreator.setOkPattern(form);
    }

    private void validate() {
        Set<ConstraintViolation<RestaurantTempRegisterForm>> violations = validator.validate(form);
        assertTrue(violations.size() != 0);
    }

    @Test
    void _正常ケース() throws Exception {
        Set<ConstraintViolation<RestaurantTempRegisterForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    void _店名が長さ0の文字列はNG() throws Exception {
        form.setName("");
        validate();
    }

    @Test
    void _店名が50字を超える場合はNG() throws Exception {
        form.setName(StringUtils.repeat("あ", 51));
        validate();
    }

    @Test
    void _店名がスペースとタブだけの場合はNG() throws Exception {
        form.setName(" 　\t");
        validate();
    }

    @Test
    void _メールアドレスが長さ0の文字列の場合はNG() throws Exception {
        form.setMailAddress("");
        validate();
    }

    @Test
    void _メールアドレスが不正の場合はNG() throws Exception {
        form.setMailAddress("iroha");
        validate();
    }

    @Test
    void _利用規約が未チェックの場合はNG() throws Exception {
        form.setAgreedTermOfUse("");
        validate();
    }

    @Test
    void _すべて未入力の場合はNG() throws Exception {
        form.setName("");
        form.setMailAddress("");
        form.setAgreedTermOfUse("");
        validate();
    }
}
