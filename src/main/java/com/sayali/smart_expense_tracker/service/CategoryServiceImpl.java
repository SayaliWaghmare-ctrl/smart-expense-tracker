package com.sayali.smart_expense_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.repository.CategoryRepository;

public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public Categories createCategory(Categories categories) {
		
		return categoryRepository.save(categories);
	}

	
	
}
