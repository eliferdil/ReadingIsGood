package com.bookstore.demo.util;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    CUSTOMER("ROLE_CUSTOMER");

    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }
}
