package com.cheeringlocalrestaurant.domain.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomGenericCollection<E> {

    private List<E> elementList;
    
    public CustomGenericCollection(final List<E> dayList) {
        this.elementList = dayList;
    }
    
    public CustomGenericCollection<E> add(E e) {
        List<E> dayList = new ArrayList<>(this.elementList);
        dayList.add(e);
        return new CustomGenericCollection<>(dayList);
    }
    
    public List<E> asList() {
        return Collections.unmodifiableList(this.elementList);
    }
}
