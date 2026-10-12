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

	@Override
	public void deleteExpense(Long id) {
		
		if(!expenseRepository.existsById(id))
		{
			throw new RuntimeException("Expense not found with id "+id);
		}
		
		expenseRepository.deleteById(id);
	}

	@Override
	public Expense getExpenseByID(Long id) {
		
		return expenseRepository.findById(id).orElseThrow(()-> new RuntimeException("expense not found with id "+id));
	}

	@Override
	public Expense updateExpense(Long id, Expense expense) {
		
		Expense existExpense = expenseRepository.findById(id).orElseThrow(()-> new RuntimeException("expense not found with id "+id));
		
		existExpense.setAmount(expense.getAmount());
		existExpense.setCategory(expense.getCategory());
		existExpense.setPaymentMethod(expense.getPaymentMethod());
		existExpense.setExpenseDate(expense.getExpenseDate());
		existExpense.setUpdatedAt(LocalDate.now());
		
		return expenseRepository.save(existExpense);
	}

}
