package com.sayali.smart_expense_tracker.service;

import com.sayali.smart_expense_tracker.entity.Expense;

public interface ExpenseService {

	Expense createExpense(Expense expense, String username);
	
	void deleteExpense(Long id);
	
	public Expense getExpenseByID(Long id);
	
	public Expense updateExpense(Long id, Expense expense);
}
