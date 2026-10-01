package com.sayali.smart_expense_tracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.entity.CategoryType;
import com.sayali.smart_expense_tracker.entity.User;

public interface CategoryRepository extends JpaRepository<Categories, Long>{

	boolean existsByUserAndName(User user, String name);
	
	List<Categories> findByUserUsernameAndType(String username, CategoryType type);

	List<Categories> findByUserUsername(String username);

	
}
