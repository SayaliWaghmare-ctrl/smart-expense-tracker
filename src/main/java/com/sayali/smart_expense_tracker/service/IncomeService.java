package com.sayali.smart_expense_tracker.service;

import com.sayali.smart_expense_tracker.entity.Income;

public interface IncomeService {

	Income createIncome(Income income, String username);
	
	void deleteIncome(Long id);
}
