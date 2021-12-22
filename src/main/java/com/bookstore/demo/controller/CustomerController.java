package com.bookstore.demo.controller;

import com.bookstore.demo.exception.CustomException;
import com.bookstore.demo.exception.CustomExceptionConstants;
import com.bookstore.demo.model.Order;
import com.bookstore.demo.model.dto.*;
import com.bookstore.demo.model.User;
import com.bookstore.demo.repository.UserRepository;
import com.bookstore.demo.service.OrderService;
import com.bookstore.demo.util.ApplicationConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "Customer Controller")
@RestController
@RequestMapping("rest/customers")
public class CustomerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "List Orders of the Customer", response = PageableOrderListDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 101, message = "User is not found")})
    @GetMapping("/{customerId}/orders/{page}")
    public PageableOrderListDto listOrders(@PathVariable long customerId, @PathVariable int page) {

        User user = userRepository.findById(customerId);
        if(user == null) {
            throw new CustomException(CustomExceptionConstants.USER_NOT_FOUND_ERROR_CODE, CustomExceptionConstants.USER_NOT_FOUND_ERROR_MSG);
        }

        Page<Order> ordersPageable =  orderService.findAllByUserIdOrderByCreatedAtDesc(customerId, PageRequest.of(page-1, ApplicationConstants.PAGE_SIZE));
        List<Order> orders = ordersPageable.getContent();

        List<OrderListDto> orderDtos = orderService.mapOrderListToDtoList(orders);

        PageableOrderListDto pageableOrderListDto = new PageableOrderListDto();
        pageableOrderListDto.setOrderList(orderDtos);
        pageableOrderListDto.setTotalNumberOfPages(ordersPageable.getTotalPages());

        return pageableOrderListDto;
    }
}
