package com.sayali.smart_expense_tracker.service;

import com.sayali.smart_expense_tracker.entity.Budget;

public interface BudgetService {

	public Budget createBudget(Budget budget, String username);
	
	public void deleteBudget(Long id);
}
