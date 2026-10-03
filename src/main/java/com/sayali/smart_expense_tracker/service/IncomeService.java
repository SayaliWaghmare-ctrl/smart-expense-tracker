package com.sayali.smart_expense_tracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.sayali.smart_expense_tracker.entity.Income;
import com.sayali.smart_expense_tracker.entity.IncomeSourceSummary;

public interface IncomeService {

	Income createIncome(Income income, String username);
	
	void deleteIncome(Long id);
	
	Income getIncomeByID(Long id);
	
	Income updateIncome(Long id, Income income);
	
	BigDecimal calculateTotalIncome(String username);
	
	BigDecimal calculateAverageIncome(String username);

	BigDecimal calculateHighestIncome(String username);

	BigDecimal calculateLowestIncome(String username);
	
	long countIncome(String username);
	
	List<IncomeSourceSummary> calculateIncomeBySource(String username);
	
	BigDecimal calculateTotalIncomeByDate(String username, LocalDate fromDate, LocalDate toDate);
}
