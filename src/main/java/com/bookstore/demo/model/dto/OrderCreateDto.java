package com.bookstore.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderCreateDto {

    @NotNull(message = "Order list must not be empty.")
    @Valid
    private List<OrderBookDto> bookList;

}
