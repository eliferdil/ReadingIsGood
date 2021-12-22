package com.bookstore.demo.service;

import com.bookstore.demo.exception.CustomException;
import com.bookstore.demo.exception.CustomExceptionConstants;
import com.bookstore.demo.model.Role;
import com.bookstore.demo.model.User;
import com.bookstore.demo.util.RoleEnum;
import com.bookstore.demo.model.dto.UserRegisterDto;
import com.bookstore.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Qualifier("adminService")
public class AdminService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegisterDto registerDto) {
        User user = userRepository.findByEmail(registerDto.getEmail());
        if(user != null) {
            throw new CustomException(CustomExceptionConstants.USER_ALREADY_EXISTS_ERROR_CODE, CustomExceptionConstants.USER_ALREADY_EXISTS_ERROR_MSG);
        }

        user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = new Role();
        role.setRoleName(RoleEnum.ADMIN.getRoleName());

        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

        return user;
    }
}
