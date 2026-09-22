package com.sayali.smart_expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/addCategory")
	public String addCategory(Model model)
	{
		model.addAttribute("categories", new Categories());

		return "category/add-category";
		
	}
	
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute("categories") Categories categories, RedirectAttributes redirectAttributes, Authentication authentication)
	{
		try {
			
		String username = authentication.getName();
		categoryService.createCategory(categories, username);
		redirectAttributes.addFlashAttribute("successMessage", "Category created successfully!");
		
		}catch(Exception e) {
			
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/category/categoryList";
		
	}
	
	@GetMapping("/categoryList")
	public String categoryList(Model model) {

		  model.addAttribute("categories", new Categories());
		  
	    // For now, just show the category list page
	    return "category/add-category";
	}
	
}
