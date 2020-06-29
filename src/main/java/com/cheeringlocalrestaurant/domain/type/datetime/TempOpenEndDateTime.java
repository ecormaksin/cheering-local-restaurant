package com.cheeringlocalrestaurant.domain.type.datetime;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class TempOpenEndDateTime extends AbstractDateTime {

    private static final long serialVersionUID = 1L;

    public TempOpenEndDateTime(String dateTimeString) {
        super(dateTimeString);
    }

}
