package com.cheeringlocalrestaurant.domain.type.account;

public enum UserRole {
    ADMIN("ROLE_ADMIN"), RESTAURANT_ADMIN("ROLE_RESTAURANT_ADMIN");

    private final String value;

    private UserRole(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
