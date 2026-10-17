package com.sayali.smart_expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sayali.smart_expense_tracker.entity.Budget;
import com.sayali.smart_expense_tracker.service.CategoryService;

@Controller
@RequestMapping("/budget")
public class BudgetController {

	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/create-budget")
	public String addBudget(Model model, Authentication authentication)
	{
		String username = authentication.getName();
		model.addAttribute("budget", new Budget()); 
		model.addAttribute("categories",categoryService.getExpenseCategoriesByUsername(username));
		   
		return "budget/create-budget";
	}
}
