package com.sayali.smart_expense_tracker.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sayali.smart_expense_tracker.entity.Expense;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.ExpenseRepository;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService{

	@Autowired
	ExpenseRepository expenseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Expense createExpense(Expense expense, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
		expense.setCreatedAt(LocalDate.now());
		expense.setUser(user);
		
		return expenseRepository.save(expense);
	}

	
}
