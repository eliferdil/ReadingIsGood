package com.bookstore.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBookDto {

    @Min(value = 1, message = "Book ID should be greater than 0.")
    private long bookId;

    @Min(value = 1, message = "Quantity value should be greater than 0.")
    private int quantity;

}
