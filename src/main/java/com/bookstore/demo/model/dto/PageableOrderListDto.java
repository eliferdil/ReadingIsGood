package com.bookstore.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableOrderListDto {

    private List<OrderListDto> orderList;

    private int totalNumberOfPages;

}
