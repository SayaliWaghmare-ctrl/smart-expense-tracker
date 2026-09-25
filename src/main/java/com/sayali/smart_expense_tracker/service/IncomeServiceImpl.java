package com.sayali.smart_expense_tracker.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sayali.smart_expense_tracker.entity.Income;
import com.sayali.smart_expense_tracker.repository.IncomeRepository;

@Service
public class IncomeServiceImpl implements IncomeService{

	@Autowired
	IncomeRepository incomeRepository;
	
	@Override
	public Income createIncome(Income income) {
		
		
		income.setCreatedAt(LocalDate.now());
		
		return incomeRepository.save(income);
	}

}
