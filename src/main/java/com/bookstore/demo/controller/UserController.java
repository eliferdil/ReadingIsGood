package com.bookstore.demo.controller;

import com.bookstore.demo.model.User;
import com.bookstore.demo.model.dto.ResponseDto;
import com.bookstore.demo.model.dto.TokenDto;
import com.bookstore.demo.util.ResponseStatusEnum;
import com.bookstore.demo.model.dto.UserRegisterDto;
import com.bookstore.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "User Controller")
@RestController
@RequestMapping("public/users")
public class UserController {

    @Autowired
    @Qualifier("adminService")
    private UserService adminService;

    @Autowired
    @Qualifier("customerService")
    private UserService customerService;

    @ApiOperation(value = "Admin Register", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 100, message = "User already exists.")})
    @PostMapping("/admin")
    public ResponseDto registerAdmin(@RequestBody @Valid UserRegisterDto registerDto) {

        User user = adminService.register(registerDto);
        return new ResponseDto(ResponseStatusEnum.SUCCESS.getStatus(), "Admin registration is successful. User ID: " + user.getId());
    }

    @ApiOperation(value = "Customer Register", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 100, message = "User already exists.")})
    @PostMapping("/customer")
    public ResponseDto registerCustomer(@RequestBody @Valid UserRegisterDto registerDto) {

        User user = customerService.register(registerDto);
        return new ResponseDto(ResponseStatusEnum.SUCCESS.getStatus(), "Customer registration is successful. User ID: " + user.getId());
    }
}
