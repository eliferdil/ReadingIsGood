package com.bookstore.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRegisterDto {

    @NotBlank(message = "Name is required.")
    private String name;

    @Min(value = 1, message = "Stock value should be greater than 0.")
    private int stock;

    @Min(value = 1, message = "Price value should be greater than 0.")
    private double price;

}
