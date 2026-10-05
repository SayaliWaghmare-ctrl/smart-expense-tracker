package com.sayali.smart_expense_tracker.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sayali.smart_expense_tracker.entity.Income;
import com.sayali.smart_expense_tracker.entity.IncomeSourceSummary;

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
	
	@Query("SELECT COUNT(i) FROM Income i WHERE i.user.username = :username AND i.date BETWEEN :fromDate AND :toDate")
	long countIncomeByDate(@Param("username") String username,@Param("fromDate") LocalDate fromDate,@Param("toDate") LocalDate toDate);
	
	@Query("SELECT new com.sayali.smart_expense_tracker.entity.IncomeSourceSummary(i.source, SUM(i.amount)) "
			+ "FROM Income i WHERE i.user.username = :username "
			+ "GROUP BY i.source")
	List<IncomeSourceSummary> calculateIncomeBySource(@Param("username") String username);
	
	@Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i WHERE i.user.username = :username AND i.date BETWEEN :fromDate AND :toDate")
	BigDecimal calculateTotalIncomeByDate(@Param("username") String username,
			                              @Param("fromDate") LocalDate fromDate,
	                                      @Param("toDate") LocalDate toDate);
}
