package com.sayali.smart_expense_tracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sayali.smart_expense_tracker.entity.Expense;

public interface ExpenseService {

	Expense createExpense(Expense expense, String username);
	
	void deleteExpense(Long id);
	
	public Expense getExpenseByID(Long id);
	
	public Expense updateExpense(Long id, Expense expense);
	
	public BigDecimal calculateTotalExpense(String username, LocalDate fromDate, LocalDate toDate);
	
	public long countExpense(String username, LocalDate fromDate, LocalDate toDate);
	
	public BigDecimal calculateAverageExpense(String username, LocalDate fromDate, LocalDate toDate);
}
