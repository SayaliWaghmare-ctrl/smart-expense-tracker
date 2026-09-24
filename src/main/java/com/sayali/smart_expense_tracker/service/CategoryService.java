package com.sayali.smart_expense_tracker.service;

import java.util.List;

import com.sayali.smart_expense_tracker.entity.Categories;

public interface CategoryService {

	Categories createCategory(Categories categories, String username);
	
	List<Categories> getAllCategory();
	
	void deleteCategory(Long id);
	
	Categories getCategoryById(Long id);
	
	Categories updateCategory(Long id, Categories categories);
}
