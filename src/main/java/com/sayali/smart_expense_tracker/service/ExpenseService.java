package com.sayali.smart_expense_tracker.service;

import com.sayali.smart_expense_tracker.entity.Expense;

public interface ExpenseService {

	Expense createExpense(Expense expense, String username);
}
