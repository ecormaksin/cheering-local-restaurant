package com.cheeringlocalrestaurant.domain.type.mail;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.type.MailAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Getter
public class CustomMailSender {

    @NonNull
    @Valid
    private CustomMailSenderName senderName;
    @NonNull
    @Valid
    private MailAddress mailAddress;
    
    public CustomMailSender(String senderName, String mailAddress) {
        this.senderName = new CustomMailSenderName(senderName);
        this.mailAddress = new MailAddress(mailAddress);
    }
    
    @Override
    public String toString() {
        return String.format("%s<%s>", senderName.getValue(), mailAddress.getValue());
    }
}
