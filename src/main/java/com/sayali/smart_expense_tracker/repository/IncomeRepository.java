package com.sayali.smart_expense_tracker.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sayali.smart_expense_tracker.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Long>{

	List<Income> findByUserUsername(String username);

	@Query("SELECT COALESCE (SUM(i.amount), 0) FROM Income i WHERE i.user.username = :username")
	BigDecimal calculateTotalIncome(@Param("username") String username);
	
	@Query("SELECT COALESCE (AVG(i.amount), 0) FROM Income i WHERE i.user.username = :username")
	BigDecimal calculateAverageIncome(@Param("username") String username);
	
	@Query("SELECT COALESCE (MAX(i.amount), 0) FROM Income i WHERE i.user.username = :username")
	BigDecimal calculateHighestIncome(@Param("username") String username);
	
	@Query("SELECT COALESCE (MIN(i.amount), 0) FROM Income i WHERE i.user.username = :username")
	BigDecimal calculateLowestIncome(@Param("username") String username);
	
	long countByUserUsername(String username);
}
