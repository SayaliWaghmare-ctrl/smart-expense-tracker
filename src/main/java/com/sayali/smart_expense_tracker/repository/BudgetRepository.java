package com.sayali.smart_expense_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sayali.smart_expense_tracker.entity.Budget;
import com.sayali.smart_expense_tracker.entity.Categories;

public interface BudgetRepository extends JpaRepository<Budget, Long>{

	boolean existsByUserUsernameAndCategoryAndMonthAndYear(String username,Categories category,Integer month,Integer year);
	
	List<Budget> findByUserUsername(String username);
	
	@Query("SELECT b FROM Budget b WHERE b.user.username = :username AND b.month = :month "
		    +"AND b.year = :year AND (:categoryId IS NULL OR b.category.id = :categoryId)")
	List<Budget> findBudgetsForOverview(@Param("username") String username,@Param("month") int month,
	                                    @Param("year") int year,@Param("categoryId") Long categoryId);
}
