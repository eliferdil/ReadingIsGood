package com.bookstore.demo.service;

import com.bookstore.demo.model.User;
import com.bookstore.demo.model.dto.UserRegisterDto;

public interface UserService {
    User register(UserRegisterDto registerDto);
}
