package com.bookstore.demo.controller;

import com.bookstore.demo.exception.CustomException;
import com.bookstore.demo.exception.CustomExceptionConstants;
import com.bookstore.demo.model.Order;
import com.bookstore.demo.model.OrderBook;
import com.bookstore.demo.model.User;
import com.bookstore.demo.model.dto.*;
import com.bookstore.demo.service.OrderService;
import com.bookstore.demo.util.ApplicationConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(value = "Order Controller")
@RestController
@RequestMapping("rest/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Persist New Order", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "There is a system error. Please try again later.")})
    @PostMapping
    public Map<Long, String> create(@RequestBody @Valid OrderCreateDto orderCreateDto, @AuthenticationPrincipal User currentUser) {
        List<OrderBookDto> bookList = orderCreateDto.getBookList();
        return orderService.processOrder(bookList, currentUser);
    }

    @ApiOperation(value = "List Order by ID", response = OrderListDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 105, message = "Order is not found.")})
    @GetMapping("/{id}")
    public OrderListDto list(@PathVariable long id) {
        OrderListDto orderDto = new OrderListDto();

        Order order = orderService.findById(id);
        if(order == null)
            throw new CustomException(CustomExceptionConstants.ORDER_NOT_FOUND_ERROR_CODE, CustomExceptionConstants.ORDER_NOT_FOUND_ERROR_MSG);

        List<OrderBook> orderBooks = order.getOrderBooks();

        List<OrderBookListDto> orderBookDtos =  orderBooks.stream().map(orderService::mapOrderBookToDto).collect(Collectors.toList());

        orderDto.setOrderDate(order.getCreatedAt());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderBookList(orderBookDtos);
        return orderDto;
    }

    @ApiOperation(value = "List Orders by Date Interval", response = PageableOrderListDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 107, message = "Date format must be yyyy-MM-dd.")})
    @GetMapping
    public PageableOrderListDto list(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int page) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime fromDate = LocalDateTime.parse(dateFrom + " 00:00:00", formatter);
            LocalDateTime toDate = LocalDateTime.parse(dateTo + " 00:00:00", formatter);

            Page<Order> ordersPageable = orderService.findByCreatedAtBetweenOrderByCreatedAtDesc(Timestamp.valueOf(fromDate), Timestamp.valueOf(toDate), PageRequest.of(page-1, ApplicationConstants.PAGE_SIZE));
            List<Order> orders = ordersPageable.getContent();

            List<OrderListDto> orderDtos = orderService.mapOrderListToDtoList(orders);

            PageableOrderListDto pageableOrderListDto = new PageableOrderListDto();
            pageableOrderListDto.setOrderList(orderDtos);
            pageableOrderListDto.setTotalNumberOfPages(ordersPageable.getTotalPages());

            return pageableOrderListDto;
        } catch (DateTimeParseException ex) {
            throw new CustomException(CustomExceptionConstants.INVALID_DATE_FORMAT_ERROR_CODE, CustomExceptionConstants.INVALID_DATE_FORMAT_ERROR_MSG);
        }
    }

}
