package com.cheeringlocalrestaurant.domain.type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TelFaxNo extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Pattern(regexp = "^0\\d{1,4}-\\d{1,4}-\\d{4}$")
    private final String value;

    public TelFaxNo(final String value) {
        this.value = value;

        this.validate(this);
    }
}
