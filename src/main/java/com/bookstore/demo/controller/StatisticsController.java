package com.bookstore.demo.controller;

import com.bookstore.demo.exception.CustomException;
import com.bookstore.demo.exception.CustomExceptionConstants;
import com.bookstore.demo.model.User;
import com.bookstore.demo.model.dto.*;
import com.bookstore.demo.repository.OrderRepository;
import com.bookstore.demo.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;

@Api(value = "Statistics Controller")
@RestController
@RequestMapping("rest/statistics")
public class StatisticsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @ApiOperation(value = "Get Customer’s Monthly Order Statistics", response = ReportResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 101, message = "User is not found."),
            @ApiResponse(code = 106, message = "Statistics data is not found.")})
    @GetMapping("/{customerId}")
    public ReportResponseDto report(@PathVariable long customerId) {

        User user = userRepository.findById(customerId);
        if(user == null) {
            throw new CustomException(CustomExceptionConstants.USER_NOT_FOUND_ERROR_CODE, CustomExceptionConstants.USER_NOT_FOUND_ERROR_MSG);
        }

        LocalDateTime now = LocalDateTime.now();
        Timestamp timestampNow = Timestamp.valueOf(now);
        Timestamp timestamp1MonthAgo = Timestamp.valueOf(now.minusMonths(1));

        ReportDto report = orderRepository.findOrderStatisticsByUserId(customerId, timestamp1MonthAgo, timestampNow);
        if(report == null) {
            throw new CustomException(CustomExceptionConstants.STATISTICS_NOT_FOUND_ERROR_CODE, CustomExceptionConstants.STATISTICS_NOT_FOUND_ERROR_MSG);
        }

        return mapToResponseDto(report);
    }

    private ReportResponseDto mapToResponseDto(ReportDto reportDto) {
        ReportResponseDto responseDto = new ReportResponseDto();
        responseDto.setTotalBookCount(reportDto.getBookCount());
        responseDto.setTotalOrderCount(reportDto.getOrderCount());
        responseDto.setTotalPurchasedAmount(BigDecimal.valueOf(reportDto.getTotalPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        responseDto.setMonth(new DateFormatSymbols().getMonths()[reportDto.getMonth() - 1]);
        return responseDto;
    }
}
