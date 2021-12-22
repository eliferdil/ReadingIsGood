package com.bookstore.demo.config;

import com.bookstore.demo.model.User;
import com.bookstore.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	@Autowired
	private UserRepository customerRepository;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
		String token = (String)authenticationToken.getCredentials();
		
		User user = customerRepository.findByToken(token);
		
		if(user == null || Calendar.getInstance().getTime().after(user.getTokenValidationDate()))
			throw new UsernameNotFoundException("Cannot find customer with authentication token=" + token);
		
		return user;
	}

}
