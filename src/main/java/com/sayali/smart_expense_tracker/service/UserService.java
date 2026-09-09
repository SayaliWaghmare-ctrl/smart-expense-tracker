package com.sayali.smart_expense_tracker.service;

import java.util.List;

import com.sayali.smart_expense_tracker.entity.User;

public interface UserService {

	User createUser(User user);
	
	List<User> getAllUsers();
	
	User getUserById(Long id);
	
	User updateUser(Long id, User user);
	
	void deleteUser(Long id);

	void forgotPassword(String email);
	
	boolean validateResetToken(String token);
	
	boolean resetPassword(String token, String newPassword);
	
	void resetPassword(String token, String newPassword, String confirmPassword);
}
