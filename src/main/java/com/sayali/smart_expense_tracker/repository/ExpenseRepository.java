package com.sayali.smart_expense_tracker.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sayali.smart_expense_tracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	List<Expense> findByUserUsername(String username);
	
	@Query("SELECT COALESCE(SUM(e.amount), 0 ) FROM Expense e WHERE e.user.username= :username AND e.expenseDate BETWEEN :fromDate AND :toDate")
	BigDecimal calculateTotalIncome(@Param("username") String username, 
			                        @Param("fromDate") LocalDate fromDate,
			                        @Param("toDate") LocalDate toDate);
	
	@Query("SELECT COUNT(e) FROM Expense e WHERE e.user.username= :username AND e.expenseDate BETWEEN :fromDate AND :toDate")
	long countExpenses(@Param("username") String username, 
                       @Param("fromDate") LocalDate fromDate,
                       @Param("toDate") LocalDate toDate);
	
	@Query("SELECT COALESCE(AVG(e.amount), 0) FROM Expense e WHERE e.user.username= :username AND e.expenseDate BETWEEN :fromDate AND :toDate")
	BigDecimal calculateAverageExpense(@Param("username") String username, 
                                       @Param("fromDate") LocalDate fromDate,
                                       @Param("toDate") LocalDate toDate);
}
