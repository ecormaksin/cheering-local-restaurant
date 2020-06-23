package com.cheeringlocalrestaurant.domain.type.date;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class OpenDay extends AbstractDate {

    private static final long serialVersionUID = 1L;
    
    public OpenDay(final String dateString) {
        super(dateString);
    }
}
