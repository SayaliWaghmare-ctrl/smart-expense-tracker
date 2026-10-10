package com.sayali.smart_expense_tracker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sayali.smart_expense_tracker.entity.Expense;
import com.sayali.smart_expense_tracker.repository.ExpenseRepository;
import com.sayali.smart_expense_tracker.service.CategoryService;
import com.sayali.smart_expense_tracker.service.ExpenseService;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ExpenseRepository expenseRepository;
	
	@GetMapping("/addExpense")
	public String showAddExpenseForm(Model model, Authentication authentication) {

	   String username = authentication.getName();
	   model.addAttribute("expense", new Expense()); 
	   model.addAttribute("categories",categoryService.getExpenseCategoriesByUsername(username));
	   return "expense/add-expense";
	    }

	@PostMapping("/saveExpense")
	public String saveExpense(@ModelAttribute("expense") Expense expense, Authentication authentication, RedirectAttributes redirectAttributes)
	{
		try {
		   String username = authentication.getName();
		   expenseService.createExpense(expense, username);
		   redirectAttributes.addFlashAttribute("successMessage", "Expense added successfully!");
		   
		}catch(Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", redirectAttributes);
		}
		return "redirect:/expense/addExpense";	
	}
	
	@GetMapping("/expense-list")
	public String viewExpense(Model model, Authentication authentication, RedirectAttributes redirectAttributes)
	{
		String username = authentication.getName();
		List<Expense> expenseList = expenseRepository.findByUserUsername(username);
		model.addAttribute("expenseList", expenseList);
		return "expense/expense-list";
	}
}
