package com.sayali.smart_expense_tracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
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
		
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: "+id));
	}

	@Override
	public User updateUser(Long id, User user) {
		
		 User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: "+id));
		 
		 existingUser.setFirstName(user.getFirstName());
		 existingUser.setLastName(user.getLastName());
		 existingUser.setEmail(user.getEmail());
		 existingUser.setUpdatedAt(LocalDateTime.now());
	//	 existingUser.setPassword(user.getPassword());
		 
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
	
	@Override
	public void forgotPassword(String email) {

	    Optional<User> userOptional = userRepository.findByEmail(email);

	    if (userOptional.isEmpty()) {
	        throw new RuntimeException("Email not registered");
	    }

	    User user = userOptional.get();

	    String token = UUID.randomUUID().toString();

	    user.setResetToken(token);
	    user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));

	    userRepository.save(user);

	    String resetLink = "http://localhost:8080/users/reset-password?token=" + token;

	    emailService.sendForgotPasswordEmail(user.getEmail(), resetLink);
	}

}
