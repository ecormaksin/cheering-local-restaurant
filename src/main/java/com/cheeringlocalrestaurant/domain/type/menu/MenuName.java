package com.cheeringlocalrestaurant.domain.type.menu;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;
import com.cheeringlocalrestaurant.validation.notallblank.NotAllBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 飲食店名
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class MenuName extends ValidationConcern {

    /** 最小文字数 */
    public static final int MIN_SIZE = 1;
    /** 最大文字数 */
    public static final int MAX_SIZE = 50;

    private static final long serialVersionUID = 1L;

    @NotAllBlank(max = MAX_SIZE)
    private final String value;

    public MenuName(final String value) {
        this.value = value;
        this.validate(this);
    }
}
