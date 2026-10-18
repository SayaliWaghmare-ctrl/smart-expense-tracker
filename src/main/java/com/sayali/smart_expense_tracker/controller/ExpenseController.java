package com.sayali.smart_expense_tracker.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.entity.CategoryType;
import com.sayali.smart_expense_tracker.entity.Expense;
import com.sayali.smart_expense_tracker.entity.ExpenseSourceSummary;
import com.sayali.smart_expense_tracker.entity.Income;
import com.sayali.smart_expense_tracker.entity.PaymentMethod;
import com.sayali.smart_expense_tracker.repository.CategoryRepository;
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
	CategoryRepository categoryRepository;
	
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
	public String viewExpense(Model model, Authentication authentication)
	{
		String username = authentication.getName();
		List<Expense> expenseList = expenseRepository.findByUserUsername(username);
		model.addAttribute("expenseList", expenseList);
		return "expense/expense-list";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteExpense(@PathVariable Long id, RedirectAttributes redirectAttributes)
	{
		try {			
			  expenseService.deleteExpense(id);
			  redirectAttributes.addFlashAttribute("successMessage", "Expense deleted successfully !");
			
		}catch(Exception e) {			
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}		
		return "redirect:/expense/expense-list";		
	}
	
	@GetMapping("/edit/{id}")
	public String updateExpenseForm(@PathVariable Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes)
	{
		String username = authentication.getName();
		Expense expense = expenseService.getExpenseByID(id);
        List<Categories> categories  = categoryRepository.findByUserUsernameAndType(username, CategoryType.EXPENSE);	
		model.addAttribute("categories", categories);
		model.addAttribute("expense", expense);
		model.addAttribute("paymentMethods", PaymentMethod.values());
		
		return "expense/edit-expense";		
	}
	
	@PostMapping("/updateExpense")
	public String updateExpenses(@ModelAttribute("expense") Expense expense, RedirectAttributes redirectAttributes)
	{
		try {
		   expenseService.updateExpense(expense.getId(), expense);
		   redirectAttributes.addFlashAttribute("successMessage", "Expenses updated successfully !");
	      
	   }catch(Exception e) {
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	  }
		
		return "redirect:/expense/expense-list";		
	}
	
	@GetMapping("/calculateExpenses")
	public String calculateExpenseForm(Model model)
	{
		return "expense/calculate-expense";	
	}
	
	@GetMapping("/calculateByDate")
	public String calculateExpensesByDate(Model model, Authentication authentication, 
			                              @RequestParam("fromDate") LocalDate fromDate,
			                              @RequestParam("toDate") LocalDate toDate)
	{
		String username = authentication.getName();
		BigDecimal totalExpense = expenseService.calculateTotalExpense(username, fromDate, toDate);
		long expenseCount = expenseService.countExpense(username, fromDate, toDate);
		BigDecimal averageExpense = expenseService.calculateAverageExpense(username, fromDate, toDate);
		BigDecimal highestExpense = expenseService.calculateHighestExpense(username, fromDate, toDate);
		BigDecimal lowestExpense = expenseService.calculateLowestExpense(username, fromDate, toDate);
		List<ExpenseSourceSummary> sourceSummaries = expenseService.calculateExxpenseBySource(username, fromDate, toDate);
		
		model.addAttribute("sourceSummaries", sourceSummaries);
		model.addAttribute("lowestExpense", lowestExpense);
		model.addAttribute("highestExpense", highestExpense);
		model.addAttribute("averageExpense", averageExpense);
		model.addAttribute("expenseCount", expenseCount);
		model.addAttribute("totalExpenses", totalExpense);
		
		return "expense/calculate-expense";
	}
}
