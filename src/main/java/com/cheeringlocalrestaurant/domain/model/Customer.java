package com.cheeringlocalrestaurant.domain.model;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.type.CustomerName;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.TelFaxNo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class Customer {

    @NonNull
    @Valid
    private CustomerName customerName;
    
    @NonNull
    @Valid
    private MailAddress mailAddress;
    
    @NonNull
    @Valid
    private TelFaxNo telNo;
}
