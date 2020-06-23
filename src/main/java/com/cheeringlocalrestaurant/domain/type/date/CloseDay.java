package com.cheeringlocalrestaurant.domain.type.date;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CloseDay extends AbstractDate {

    private static final long serialVersionUID = 1L;
    
    public CloseDay(final String dateString) {
        super(dateString);
    }
}
