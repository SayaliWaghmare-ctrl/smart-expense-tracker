package com.sayali.smart_expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	JavaMailSender mailSender;

	@Override
	public void sendForgotPasswordEmail(String toEmail, String resetLink) {
		
		 SimpleMailMessage message = new SimpleMailMessage();

	        message.setTo(toEmail);
	        message.setSubject("Smart Expense Tracker - Reset Password");

	        message.setText(
	                "Hello,\n\n"
	                + "We received a request to reset your Smart Expense Tracker password.\n\n"
	                + "Click the link below to reset your password:\n\n"
	                + resetLink
	                + "\n\n"
	                + "This link will expire after 15 minutes.\n\n"
	                + "If you did not request a password reset, please ignore this email.\n\n"
	                + "Regards,\n"
	                + "Smart Expense Tracker Team"
	        );

	        mailSender.send(message);
		
	}

}
