package com.cheeringlocalrestaurant.domain.type.datetime;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DesiredReceiptDateTime extends AbstractDateTime {

    private static final long serialVersionUID = 1L;

    public DesiredReceiptDateTime(String dateTimeString) {
        super(dateTimeString);
    }

}
