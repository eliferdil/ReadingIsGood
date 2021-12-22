package com.bookstore.demo.util;

import lombok.Getter;

@Getter
public enum ResponseStatusEnum {
    SUCCESS("Success"),
    ERROR("Error");

    private String status;

    ResponseStatusEnum(String status) {
        this.status = status;
    }
}
