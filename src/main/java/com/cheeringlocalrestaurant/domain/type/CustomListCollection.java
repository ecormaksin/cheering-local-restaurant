package com.cheeringlocalrestaurant.domain.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomListCollection<E> {

    private List<E> elementList;
    
    public CustomListCollection(final List<E> dayList) {
        this.elementList = dayList;
    }
    
    public CustomListCollection<E> add(E e) {
        List<E> dayList = new ArrayList<>(this.elementList);
        dayList.add(e);
        return new CustomListCollection<>(dayList);
    }
    
    public List<E> asList() {
        return Collections.unmodifiableList(this.elementList);
    }
}
