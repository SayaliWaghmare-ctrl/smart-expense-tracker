package com.sayali.smart_expense_tracker.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.repository.CategoryRepository;
import com.sayali.smart_expense_tracker.repository.UserRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Categories createCategory(Categories categories, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
		categories.setUser(user);
		categories.setCreatedAt(LocalDateTime.now());
		
		if(categoryRepository.existsByUserAndName(user, categories.getName()))
		{
			throw new RuntimeException("Category already exists");
		}
		return categoryRepository.save(categories);
	}

	@Override
	public List<Categories> getAllCategory() {
	
		return categoryRepository.findAll();
	}

	@Override
	public void deleteCategory(Long id) {
		
		if(!categoryRepository.existsById(id))
		{
			throw new RuntimeException("Category not found with id: "+id);	
		}
		
		categoryRepository.deleteById(id);
	}

	
	
}
