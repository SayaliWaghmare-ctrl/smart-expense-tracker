package com.sayali.smart_expense_tracker.service;

import java.math.BigDecimal;

import com.sayali.smart_expense_tracker.entity.Income;

public interface IncomeService {

	Income createIncome(Income income, String username);
	
	void deleteIncome(Long id);
	
	Income getIncomeByID(Long id);
	
	Income updateIncome(Long id, Income income);
	
	BigDecimal calculateTotalIncome(String username);
}
