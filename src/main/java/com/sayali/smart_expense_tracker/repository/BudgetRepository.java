package com.sayali.smart_expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayali.smart_expense_tracker.entity.Budget;
import com.sayali.smart_expense_tracker.entity.Categories;

public interface BudgetRepository extends JpaRepository<Budget, Long>{

	boolean existsByUserUsernameAndCategoryAndMonthAndYear(String username,Categories category,
	                                                       Integer month,Integer year);
}
