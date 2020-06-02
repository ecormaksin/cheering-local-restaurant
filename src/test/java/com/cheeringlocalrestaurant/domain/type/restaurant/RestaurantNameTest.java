package com.cheeringlocalrestaurant.domain.type.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

class RestaurantNameTest {

    @Test
    void _正常な場合は例外が発生しない() {
        try {
            new RestaurantName("いろは食堂");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void _nullの場合は例外が発生する() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RestaurantName(null);
        });
    }

    @Test
    void _長さ0の場合は例外が発生する() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RestaurantName("");
        });
    }

    @Test
    void _半角スペースのみの場合は例外が発生する() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RestaurantName(" ");
        });
    }

    @Test
    void _全角スペースのみの場合は例外が発生する() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RestaurantName("　");
        });
    }

    @Test
    void _タブのみの場合は例外が発生する() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RestaurantName("\t");
        });
    }

    @Test
    void _50文字を超える場合は例外が発生する() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RestaurantName(StringUtils.repeat("あ", 51));
        });
    }
}
