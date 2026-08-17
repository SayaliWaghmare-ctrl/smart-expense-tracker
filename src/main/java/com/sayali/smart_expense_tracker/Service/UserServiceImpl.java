package com.sayali.smart_expense_tracker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User createUser(User user) {
		
		user.setCreatedAt(LocalDateTime.now());
		
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
	
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("USer not found with id: "+id));
	}

	@Override
	public User updateUser(Long id, User user) {
		
		 User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: "+id));
		 
		 existingUser.setFirstName(user.getFirstName());
		 existingUser.setLastName(user.getLastName());
		 existingUser.setEmail(user.getEmail());
		 existingUser.setPassword(user.getPassword());
		 return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		
		if(!userRepository.existsById(id))
		{
			throw new RuntimeException("User not found with id: "+id);			
			
		}
		
		 userRepository.deleteById(id);
	}
	
}
