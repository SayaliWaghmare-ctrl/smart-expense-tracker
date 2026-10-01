package com.sayali.smart_expense_tracker.controller;

import java.math.BigDecimal;
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

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.sayali.smart_expense_tracker.entity.Categories;
import com.sayali.smart_expense_tracker.entity.CategoryType;
import com.sayali.smart_expense_tracker.entity.Income;
import com.sayali.smart_expense_tracker.repository.CategoryRepository;
import com.sayali.smart_expense_tracker.repository.IncomeRepository;
import com.sayali.smart_expense_tracker.service.CategoryService;
import com.sayali.smart_expense_tracker.service.IncomeService;


@Controller
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	IncomeService incomeService;
	
	@Autowired
	IncomeRepository incomeRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("/addIncome")
	public String createIncome(Model model, Income income, Authentication authentication)
	{
		String username = authentication.getName();
		List<Categories> categories  = categoryRepository.findByUserUsernameAndType(username, CategoryType.INCOME);
		
		model.addAttribute("categories", categories);
		model.addAttribute("income", new Income());
		return "income/add-income";		
	}
	
	@PostMapping("/saveIncome")
	public String saveIncome(@ModelAttribute("income") Income income, RedirectAttributes redirectAttributes, Authentication authentication)
	{
		try {
			
			String username = authentication.getName();
			incomeService.createIncome(income, username);			
			redirectAttributes.addFlashAttribute("successMessage", "Income created successfully !");
			
		}catch(Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/income/addIncome";	
	}
	
	@GetMapping("/incomeList")
	public String getIncomeList(Model model, RedirectAttributes redirectAttributes, Authentication authentication)
	{		
		try {
			
		String username = authentication.getName();	
		List<Income> incomeList= incomeRepository.findByUserUsername(username);
		model.addAttribute("incomeList", incomeList);
		
		}catch(Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "income/income-list";		
	}
	
	@PostMapping("/delete/{id}")
	public String deleteIncome(@PathVariable Long id, RedirectAttributes redirectAttributes)
	{
		try {
			
		incomeService.deleteIncome(id);
		redirectAttributes.addFlashAttribute("successMessage", "Income deleted successfully !");
		
		}catch(Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/income/incomeList";		
	}
	
	@GetMapping("/edit/{id}")
	public String editIncome(@PathVariable Long id, Model model, Authentication authentication, RedirectAttributes redirectAttributes)
	{
		try {
			
		String username = authentication.getName();
		Income income =  incomeService.getIncomeByID(id);
        List<Categories> categories  = categoryRepository.findByUserUsernameAndType(username, CategoryType.INCOME);
		
		model.addAttribute("categories", categories);
		model.addAttribute("income", income);
		
		}catch(Exception e)
		{
			redirectAttributes.addFlashAttribute("successMessage", e.getMessage());
		}
		return "income/edit-income";		
	}
	
	@PostMapping("/updateIncome")
	public String editIncomeForm(@ModelAttribute("income") Income income, RedirectAttributes redirectAttributes)
	{
		try {
			
		    incomeService.updateIncome(income.getId(), income);
		    redirectAttributes.addFlashAttribute("successMessage", "Income updated successfully !");
		      
		}catch(Exception e) {
			
			redirectAttributes.addFlashAttribute("successMessage", e.getMessage());
		}
		return "redirect:/income/incomeList";
	}
	
	@GetMapping("/calculate")
	public String calculateIncome(Model model, Authentication authentication)
	{
		String username = authentication.getName();
		BigDecimal totalIncome = incomeService.calculateTotalIncome(username);
		
		model.addAttribute("totalIncome", totalIncome);
		return "income/calculate-income";		
	}
}
