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
	
	BigDecimal calculateAverageIncome(String username, LocalDate fromDate, LocalDate toDate);

	BigDecimal calculateHighestIncome(String username, LocalDate fromDate, LocalDate toDate);

	BigDecimal calculateLowestIncome(String username, LocalDate fromDate, LocalDate toDate);
	
	long countIncome(String username, LocalDate fromDate, LocalDate toDate);
	
	List<IncomeSourceSummary> calculateIncomeBySource(String username, LocalDate fromDate, LocalDate toDate);
	
	BigDecimal calculateTotalIncomeByDate(String username, LocalDate fromDate, LocalDate toDate);
}
