package com.cheeringlocalrestaurant.domain.type.datetime;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class TempOpenStartDateTime extends AbstractDateTime {

    private static final long serialVersionUID = 1L;

    public TempOpenStartDateTime(String dateTimeString) {
        super(dateTimeString);
    }

}
