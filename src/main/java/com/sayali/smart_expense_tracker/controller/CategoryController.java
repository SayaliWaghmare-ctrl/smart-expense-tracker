package com.sayali.smart_expense_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sayali.smart_expense_tracker.entity.Categories;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@GetMapping("/addCategory")
	public String addCategory(Model model)
	{
		  model.addAttribute("category", new Categories());

		return "category/add-category";
		
	}
	
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute("categories") Categories categories, RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
		
		return "redirect:/category/categoryList";
		
	}
}
