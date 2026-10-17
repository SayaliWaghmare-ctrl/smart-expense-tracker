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
import com.sayali.smart_expense_tracker.entity.Budget;
import com.sayali.smart_expense_tracker.service.BudgetService;
import com.sayali.smart_expense_tracker.service.CategoryService;

@Controller
@RequestMapping("/budget")
public class BudgetController {

	@Autowired
	BudgetService budgetService;
	
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
	
	@PostMapping("/save-budget")
	public String saveBudget(@ModelAttribute("budget") Budget budget, Authentication authentication, RedirectAttributes redirectAttributes)
	{
		try {
		  String username = authentication.getName();
		  budgetService.createBudget(budget, username);
		  redirectAttributes.addFlashAttribute("successMessage", "Budget created successfully !");
		  
		}catch(Exception e) {
			
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		
		return "redirect:/budget/create-budget";
	}
}
