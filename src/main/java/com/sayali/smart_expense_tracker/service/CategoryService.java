package com.sayali.smart_expense_tracker.service;

import com.sayali.smart_expense_tracker.entity.Categories;

public interface CategoryService {

	Categories createCategory(Categories categories, String username);
}
