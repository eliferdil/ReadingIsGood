package com.bookstore.demo.controller;

import com.bookstore.demo.exception.CustomException;
import com.bookstore.demo.exception.CustomExceptionConstants;
import com.bookstore.demo.model.dto.LoginDto;
import com.bookstore.demo.model.dto.TokenDto;
import com.bookstore.demo.model.User;
import com.bookstore.demo.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.UUID;

@Api(value = "Authentication Controller")
@RestController
@RequestMapping("public/authentication")
public class AuthenticationController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@ApiOperation(value = "Login", response = TokenDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 101, message = "User is not found.")})
	@PostMapping("/login")
	public TokenDto login(@RequestBody @Valid LoginDto loginDto) {

		User user = userRepository.findByEmail(loginDto.getEmail());
		if(user == null || passwordEncoder.matches(user.getPassword(), loginDto.getPassword())) {
			throw new CustomException(CustomExceptionConstants.USER_NOT_FOUND_ERROR_CODE, CustomExceptionConstants.USER_NOT_FOUND_ERROR_MSG);
		}

		UUID token = UUID.randomUUID();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 15);

		user.setToken(token.toString());
		user.setTokenValidationDate(cal.getTime());
		userRepository.save(user);

		return new TokenDto(token.toString());
	}
}
