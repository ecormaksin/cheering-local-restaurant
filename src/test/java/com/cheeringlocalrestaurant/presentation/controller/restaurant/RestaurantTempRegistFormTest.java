package com.cheeringlocalrestaurant.presentation.controller.restaurant;

import com.cheeringlocalrestaurant.CheeringLocalRestaurantApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CheeringLocalRestaurantApplication.class)
public class RestaurantTempRegistFormTest {

    @Autowired
    private Validator validator;

    private String nameNotBlankWithSizeMessage= "{0}'s size must be between 1 and 50. And string containing only blanks is not allowed.";

    private RestaurantTempRegistForm form = new RestaurantTempRegistForm();
    private BindingResult bindingResult = new BindException(form, "restaurantTempRegistForm");

    @BeforeEach
    public void setUp() {
        form.setName("いろは食堂");
        form.setMailAddress("iroha@example.com");
        form.setAgreedTermOfUse(true);
    }

    @Test
    void _正常ケース() throws Exception {
        validator.validate(form, bindingResult);
        assertNull(bindingResult.getFieldError());
    }

    @Test
    void _店名がnullはNG() throws Exception {
        form.setName(null);
        validator.validate(form, bindingResult);
        FieldError fieldError = bindingResult.getFieldError();
        assertEquals("name", fieldError.getField());
        assertEquals(nameNotBlankWithSizeMessage, fieldError.getDefaultMessage());
    }

    @Test
    void _店名が長さ0の文字列はNG() throws Exception {
        form.setName("");
        validator.validate(form, bindingResult);
        FieldError fieldError = bindingResult.getFieldError();
        assertEquals("name", fieldError.getField());
        assertEquals(nameNotBlankWithSizeMessage, fieldError.getDefaultMessage());
    }

    @Test
    void _店名が半角スペース1文字はNG() throws Exception {
        form.setName(" ");
        validator.validate(form, bindingResult);
        FieldError fieldError = bindingResult.getFieldError();
        assertEquals("name", fieldError.getField());
        assertEquals(nameNotBlankWithSizeMessage, fieldError.getDefaultMessage());
    }
}
