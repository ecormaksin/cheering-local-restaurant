package com.cheeringlocalrestaurant.presentation.controller.restaurant;

public class RestaurantTempRegisterFormCreator {

    public static RestaurantTempRegisterForm getOkPattern() {

        RestaurantTempRegisterForm form = new RestaurantTempRegisterForm();
        setOkPattern(form);
        return form;
    }

    public static void setOkPattern(RestaurantTempRegisterForm form) {
        form.setName("いろは食堂");
        form.setMailAddress("iroha@example.com");
        form.setAgreedTermOfUse(true);
    }
}
