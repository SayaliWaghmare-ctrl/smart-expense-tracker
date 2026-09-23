package com.sayali.smart_expense_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	    return "category/add-category";
	}
	
	@GetMapping("/categoryListForm")
	public String categoryListForm(Model model) {

		List<Categories> categories = categoryService.getAllCategory();		
		model.addAttribute("categories", categories);
	    return "category/category-list";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes)
	{
		categoryService.deleteCategory(id);
		redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
		return "redirect:/category/categoryListForm";
		
	}
}
