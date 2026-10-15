package com.sayali.smart_expense_tracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.sayali.smart_expense_tracker.entity.Expense;
import com.sayali.smart_expense_tracker.entity.ExpenseSourceSummary;

public interface ExpenseService {

	Expense createExpense(Expense expense, String username);
	
	void deleteExpense(Long id);
	
	public Expense getExpenseByID(Long id);
	
	public Expense updateExpense(Long id, Expense expense);
	
	public BigDecimal calculateTotalExpense(String username, LocalDate fromDate, LocalDate toDate);
	
	public long countExpense(String username, LocalDate fromDate, LocalDate toDate);
	
	public BigDecimal calculateAverageExpense(String username, LocalDate fromDate, LocalDate toDate);
	
	public BigDecimal calculateHighestExpense(String username, LocalDate fromDate, LocalDate toDate);
	
	public BigDecimal calculateLowestExpense(String username, LocalDate fromDate, LocalDate toDate);
	
	public List<ExpenseSourceSummary> calculateExxpenseBySource(String username, LocalDate fromDate, LocalDate toDate);
}
