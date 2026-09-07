package com.sayali.smart_expense_tracker.service;

public interface EmailService {

	void sendForgotPasswordEmail(String toEmail, String resetLink);
}
