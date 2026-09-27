package com.sayali.smart_expense_tracker.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sayali.smart_expense_tracker.entity.Income;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.IncomeRepository;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class IncomeServiceImpl implements IncomeService{

	@Autowired
	IncomeRepository incomeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Income createIncome(Income income, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
		income.setCreatedAt(LocalDate.now());
		income.setUser(user);
		return incomeRepository.save(income);
	}

}
