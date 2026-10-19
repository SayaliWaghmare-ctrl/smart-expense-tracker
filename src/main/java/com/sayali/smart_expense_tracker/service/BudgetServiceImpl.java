package com.sayali.smart_expense_tracker.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sayali.smart_expense_tracker.entity.Budget;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.BudgetRepository;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class BudgetServiceImpl implements BudgetService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BudgetRepository budgetRepository;
	
	@Override
	public Budget createBudget(Budget budget, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        
		if(budgetRepository.existsByUserUsernameAndCategoryAndMonthAndYear(username, budget.getCategory(), budget.getMonth(), budget.getYear()))
		  {
			 throw new RuntimeException("This budget already exists for the selected category and month!");       
		  }
		
		budget.setUser(user);
        budget.setCreatedAt(LocalDate.now());
        
		return budgetRepository.save(budget);
	}

	@Override
	public void deleteBudget(Long id) {
		
		if(!budgetRepository.existsById(id))
		{
			throw new RuntimeException("Budget not found with Id "+id);
		}
		
		budgetRepository.deleteById(id);
	}

	@Override
	public Budget updateBudget(Long id, Budget budget) {
		
		Budget existBudget = budgetRepository.findById(id).orElseThrow(()-> new RuntimeException("Budget not found with id "+id));
		
		existBudget.setAmount(budget.getAmount());
		existBudget.setMonth(budget.getMonth());
		existBudget.setYear(budget.getYear());
		existBudget.setCategory(budget.getCategory());
		
		return budgetRepository.save(existBudget);
	}

	@Override
	public Budget getBudgetById(Long id) {
		
		return budgetRepository.findById(id).orElseThrow(() -> new RuntimeException("Budget not found with id "+id));
	}

	
	
}
