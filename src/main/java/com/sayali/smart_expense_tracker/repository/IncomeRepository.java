package com.sayali.smart_expense_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Long>{

	
	
}
