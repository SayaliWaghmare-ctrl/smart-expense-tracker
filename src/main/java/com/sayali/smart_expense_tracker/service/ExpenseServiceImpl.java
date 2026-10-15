package com.sayali.smart_expense_tracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sayali.smart_expense_tracker.entity.Expense;
import com.sayali.smart_expense_tracker.entity.ExpenseSourceSummary;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.ExpenseRepository;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService{

	@Autowired
	ExpenseRepository expenseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Expense createExpense(Expense expense, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
		expense.setCreatedAt(LocalDate.now());
		expense.setUser(user);
		
		return expenseRepository.save(expense);
	}

	@Override
	public void deleteExpense(Long id) {
		
		if(!expenseRepository.existsById(id))
		{
			throw new RuntimeException("Expense not found with id "+id);
		}
		
		expenseRepository.deleteById(id);
	}

	@Override
	public Expense getExpenseByID(Long id) {
		
		return expenseRepository.findById(id).orElseThrow(()-> new RuntimeException("expense not found with id "+id));
	}

	@Override
	public Expense updateExpense(Long id, Expense expense) {
		
		Expense existExpense = expenseRepository.findById(id).orElseThrow(()-> new RuntimeException("expense not found with id "+id));
		
		existExpense.setAmount(expense.getAmount());
		existExpense.setCategory(expense.getCategory());
		existExpense.setPaymentMethod(expense.getPaymentMethod());
		existExpense.setExpenseDate(expense.getExpenseDate());
		existExpense.setSource(expense.getSource());
		existExpense.setUpdatedAt(LocalDate.now());
		
		return expenseRepository.save(existExpense);
	}

	@Override
	public BigDecimal calculateTotalExpense(String username, LocalDate fromDate, LocalDate toDate) {
		
		return expenseRepository.calculateTotalExpense(username, fromDate, toDate);
	}

	@Override
	public long countExpense(String username, LocalDate fromDate, LocalDate toDate) {
		
		return expenseRepository.countExpenses(username, fromDate, toDate);
	}

	@Override
	public BigDecimal calculateAverageExpense(String username, LocalDate fromDate, LocalDate toDate) {
		
		return expenseRepository.calculateAverageExpense(username, fromDate, toDate);
	}

	@Override
	public BigDecimal calculateHighestExpense(String username, LocalDate fromDate, LocalDate toDate) {
		
		return expenseRepository.calculateHighestExpense(username, fromDate, toDate);
	}

	@Override
	public BigDecimal calculateLowestExpense(String username, LocalDate fromDate, LocalDate toDate) {
		
		return expenseRepository.calculateLowestExpense(username, fromDate, toDate);
	}

	@Override
	public List<ExpenseSourceSummary> calculateExxpenseBySource(String username, LocalDate fromDate, LocalDate toDate) {
		
		return expenseRepository.calculateExpenseSummary(username, fromDate, toDate);
	}
}
