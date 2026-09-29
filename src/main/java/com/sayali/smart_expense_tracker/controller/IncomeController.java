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
	public String getIncomeList(Model model, RedirectAttributes redirectAttributes)
	{		
		try {
		List<Income> incomeList= incomeRepository.findAll();
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
}
