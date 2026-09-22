package com.sayali.smart_expense_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.entity.User;

public interface CategoryRepository extends JpaRepository<Categories, Long>{

	boolean existsByUserAndName(User user, String name);
}
