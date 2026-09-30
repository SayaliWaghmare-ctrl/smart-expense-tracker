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

	@Override
	public void deleteIncome(Long id) {
		
		if(!incomeRepository.existsById(id))
		{
			throw new RuntimeException("Income not found with id "+id);
		}
		
		incomeRepository.deleteById(id);
	}

	@Override
	public Income getIncomeByID(Long id) {
		
		return incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Income not found with Id "+id));
		
	}

	@Override
	public Income updateIncome(Long id, Income income) {
		
		Income existIncome = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Income not found with Id "+id));
		existIncome.setAmount(income.getAmount());
		existIncome.setSource(income.getSource());
		existIncome.setCategory(income.getCategory());
		existIncome.setDate(income.getDate());
		existIncome.setDescription(income.getDescription());
		existIncome.setUpdatedAt(LocalDate.now());
		
		return incomeRepository.save(existIncome);
	}

}
