package com.sayali.smart_expense_tracker.service;

import org.jspecify.annotations.Nullable;

import com.sayali.smart_expense_tracker.entity.Budget;

public interface BudgetService {

	public Budget createBudget(Budget budget, String username);
	
	public void deleteBudget(Long id);
	
	public Budget updateBudget(Long id, Budget budget);
	
	public Budget getBudgetById(Long id);

	public @Nullable Object getCategoryBudgets(String username, Integer month, Integer year);
	
}
