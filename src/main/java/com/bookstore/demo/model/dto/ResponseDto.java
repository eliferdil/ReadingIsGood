package com.bookstore.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private int code;
    private String message;

    public ResponseDto(String status) {
        this.status = status;
    }

    public ResponseDto(String status, String message) {
        this(status);
        this.message = message;
    }

    public ResponseDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
