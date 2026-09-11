package com.sayali.smart_expense_tracker.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService{

	 private final UserRepository userRepository;
	 private final PasswordEncoder passwordEncoder;
	 	 
	 public LoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	 
	
	@Override
	public boolean validateLogin(String username, String password) {


		Optional<User> userOptional = userRepository.findByUsername(username);
		
		if(userOptional.isPresent())
		{
			User user = userOptional.get();
			//return user.getPassword().equals(password);
			return passwordEncoder.matches(password, user.getPassword());
		}
		return false;
	}
}
