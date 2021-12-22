package com.bookstore.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderListDto {

    private List<OrderBookListDto> orderBookList;

    private double totalPrice;

    private Date orderDate;

}
