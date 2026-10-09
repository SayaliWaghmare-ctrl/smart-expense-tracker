package com.sayali.smart_expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayali.smart_expense_tracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	
}
