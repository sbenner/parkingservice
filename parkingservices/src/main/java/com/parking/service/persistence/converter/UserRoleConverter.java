package com.parking.service.persistence.converter;

import com.parking.service.persistence.model.enums.UserRole;

import javax.persistence.AttributeConverter;

public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    public String convertToDatabaseColumn(UserRole t) {
        return t != null ? t.getName() : null;
    }

    public UserRole convertToEntityAttribute(String name) {
        return UserRole.valueOf(name.toUpperCase());
    }
}

