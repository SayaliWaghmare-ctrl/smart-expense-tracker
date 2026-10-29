package com.sayali.smart_expense_tracker.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

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
import com.sayali.smart_expense_tracker.entity.Budget;
import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.entity.CategoryType;
import com.sayali.smart_expense_tracker.repository.BudgetRepository;
import com.sayali.smart_expense_tracker.repository.CategoryRepository;
import com.sayali.smart_expense_tracker.service.BudgetService;
import com.sayali.smart_expense_tracker.service.CategoryService;

@Controller
@RequestMapping("/budget")
public class BudgetController {

	@Autowired
	BudgetService budgetService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	BudgetRepository budgetRepository;
	
	@GetMapping("/create-budget")
	public String addBudget(Model model, Authentication authentication)
	{
		String username = authentication.getName();
		model.addAttribute("budget", new Budget()); 
		model.addAttribute("categories",categoryService.getExpenseCategoriesByUsername(username));
		   
		return "budget/create-budget";
	}
	
	@PostMapping("/save-budget")
	public String saveBudget(@ModelAttribute("budget") Budget budget,Authentication authentication,RedirectAttributes redirectAttributes)
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
	
	@GetMapping("/budget-list")
	public String budgetList(Model model,Authentication authentication)
	{
		String username = authentication.getName();
		List<Budget> budgetList = budgetRepository.findByUserUsername(username);
		model.addAttribute("budgetList", budgetList);
		return "budget/budget-list";	
	}
	
	@PostMapping("/delete/{id}")
	public String deleteBudget(@PathVariable Long id, RedirectAttributes redirectAttributes)
	{
		try {   
		   budgetService.deleteBudget(id);
		   redirectAttributes.addFlashAttribute("successMessage", "Budget deleted successfully !");
		   
		}catch(Exception e) {
			
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/budget/budget-list";		
	}
	
	@GetMapping("/edit/{id}")
	public String editBudgetForm(@PathVariable Long id, Model model, Authentication authentication)
	{
		String username = authentication.getName();
		Budget budget = budgetService.getBudgetById(id);
        List<Categories> categories  = categoryRepository.findByUserUsernameAndType(username, CategoryType.EXPENSE);	
        model.addAttribute("categories", categories);
		model.addAttribute("budget", budget);
		
		return "budget/edit-budget";	
	}
	
	@PostMapping("/update-budget")
	public String editBudget(@ModelAttribute("budget") Budget budget, RedirectAttributes redirectAttributes)
	{
		try {
		   budgetService.updateBudget(budget.getId(), budget);
		   redirectAttributes.addFlashAttribute("successMessage", "Budget updated successfully !");
		
		}catch(Exception e) {
			
			   redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/budget/budget-list";		
	}
	
	@GetMapping("/budget-overview")
	public String budgetOverview( @RequestParam(required = false) Integer month,@RequestParam(required = false) Integer year,
	                              Model model,@RequestParam(required = false)Long categoryId,Authentication authentication)
	{
		 // Set default month and year
	    LocalDate today = LocalDate.now();
	    int currentYear = LocalDate.now().getYear();
	    
	    if (month == null) {
	        month = today.getMonthValue();
	    }

	    if (year == null) {
	        year = currentYear;
	    }

	    // Generate years for dropdown
	    List<Integer> availableYears = IntStream
	            .rangeClosed(currentYear - 2, currentYear + 2)
	            .boxed()
	            .sorted(Comparator.reverseOrder())
	            .toList();
	    
	    BigDecimal budgetAmount= BigDecimal.TEN;
	    BigDecimal spentAmount= BigDecimal.TWO;
	    BigDecimal remainingAmount = budgetAmount.subtract(spentAmount);

	    BigDecimal usagePercentage = BigDecimal.ZERO;
	    
	    String username = authentication.getName();

	    List<Budget> categoryBudgets = budgetService.getBudgetsForOverview(authentication.getName(), month, year, categoryId);
	    
	    // Category List
        List<Categories> categories  = categoryRepository.findByUserUsernameAndType(username, CategoryType.EXPENSE);

	    
	    // Add your actual service methods here
        model.addAttribute("categories", categories);
        model.addAttribute("categoryBudgets", categoryBudgets);
        model.addAttribute("availableYears", availableYears);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("budgetAmount", budgetAmount);
        model.addAttribute("spentAmount", spentAmount);
        model.addAttribute("remainingAmount", remainingAmount);
        model.addAttribute("usagePercentage", usagePercentage);
	    
	    // IMPORTANT: Always provide a list, even if it is empty
	    model.addAttribute("categoryBudgets",budgetService.getCategoryBudgets(username, month, year));

		return "budget/budget-overview";	
	}
}
