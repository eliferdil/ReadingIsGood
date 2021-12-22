package com.bookstore.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class BookUpdateDto {

    @Min(value = 1, message = "ID should be greater than 0.")
    private long id;

    @Min(value = 1, message = "Stock value should be greater than 0.")
    private int stock;

}
