package com.sayali.smart_expense_tracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sayali.smart_expense_tracker.entity.PasswordResetToken;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.PasswordResetTokenRepository;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordResetTokenRepository tokenRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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

	    // Generate new token
	    String token = UUID.randomUUID().toString();

	    // Create PasswordResetToken object
	    PasswordResetToken resetToken = new PasswordResetToken();
	    
	    resetToken.setToken(token);
	    resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
	    resetToken.setUser(user);
	    
	    tokenRepository.save(resetToken);

	    String resetLink = "http://localhost:8080/users/forgot-password-form?token=" + token;

	    emailService.sendForgotPasswordEmail(user.getEmail(), resetLink);
	}

	@Override
	public boolean validateResetToken(String token) {
		
		Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
		
		if(tokenOptional.isEmpty())
		{
			return false;
		}
		
		PasswordResetToken resetToken = tokenOptional.get();
		
		if(resetToken.getExpiryDate().isBefore(LocalDateTime.now()))
		{
			tokenRepository.deleteByToken(token);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean resetPassword(String token, String newPassword) {
		
		if(!validateResetToken(token))
		{
			return false;
		}
		
		Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
		PasswordResetToken resetToken = tokenOptional.get();
		User user = resetToken.getUser();
		
		user.setPassword(passwordEncoder.encode(newPassword));
		
		userRepository.save(user);
		tokenRepository.deleteByToken(token);
		
		return true;
	}

	private void validatePassword(String password) {
	    
	    if (password == null || password.isBlank()) {
	        throw new RuntimeException("Password cannot be empty");
	    }

	    if (password.length() < 8) {
	        throw new RuntimeException("Password must contain at least 8 characters");
	    }

	    if (!password.matches(".*[A-Z].*")) {
	        throw new RuntimeException(
	                "Password must contain at least one uppercase letter");
	    }

	    if (!password.matches(".*[a-z].*")) {
	        throw new RuntimeException(
	                "Password must contain at least one lowercase letter");
	    }

	    if (!password.matches(".*\\d.*")) {
	        throw new RuntimeException(
	                "Password must contain at least one number");
	    }

	    if (!password.matches(".*[@#$%^&+=!].*")) {
	        throw new RuntimeException(
	                "Password must contain at least one special character");
	    }
	}

	@Override
	@Transactional
	public void resetPassword(String token, String newPassword, String confirmPassword) {
		
		// Password validation
	    validatePassword(newPassword);

	    // Confirm password validation
	    if (!newPassword.equals(confirmPassword)) {
	        throw new RuntimeException("Passwords do not match");
	    }

	    // Find reset token
	    Optional<PasswordResetToken> tokenOptional =
	            tokenRepository.findByToken(token);

	    if (tokenOptional.isEmpty()) {
	        throw new RuntimeException("Invalid reset link");
	    }

	    PasswordResetToken resetToken = tokenOptional.get();

	    // Check expiry
	    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {

	    	tokenRepository.deleteByToken(token);

	        throw new RuntimeException("Reset link has expired");
	    }

	    // Get user from token
	    User user = resetToken.getUser();

	    // Update password
	    user.setPassword(newPassword);

	    userRepository.save(user);

	    // Delete token after successful password reset
	    tokenRepository.deleteByToken(token);
	}

	
}
