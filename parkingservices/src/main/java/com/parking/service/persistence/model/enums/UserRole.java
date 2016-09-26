package com.parking.service.persistence.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
    //use the UPPER case ROLE in the DATABASE as it's written here

    ADMIN("ADMIN"), REGULAR("REGULAR"), PREMIUM("PREMIUM");

    private String name;

    public String getName() {
        return name.toUpperCase();
    }

    private UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @JsonCreator
    public static UserRole fromString(String name) {
        UserRole role = UserRole.valueOf(name.toUpperCase());
        if (role == null) {
            throw new IllegalArgumentException(name + " has no corresponding value");
        }
        return role;
    }


}
