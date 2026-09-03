package com.sayali.smart_expense_tracker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService{

	 private final UserRepository userRepository;
	 
	 public LoginServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean validateLogin(String username, String password) {


		Optional<User> userOptional = userRepository.findByUsername(username);
		
		if(userOptional.isPresent())
		{
			User user = userOptional.get();
			return user.getPassword().equals(password);
		}
		return false;
	}
}
