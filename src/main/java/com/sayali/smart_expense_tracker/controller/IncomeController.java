package com.sayali.smart_expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sayali.smart_expense_tracker.entity.Income;
import com.sayali.smart_expense_tracker.service.IncomeService;


@Controller
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	IncomeService incomeService;
	
	@GetMapping("/addIncome")
	public String createIncome(Model model)
	{
		model.addAttribute("income", new Income());
		return "income/add-income";
		
	}
	
	@PostMapping("/saveIncome")
	public String saveIncome(@ModelAttribute("income") Income income, RedirectAttributes redirectAttributes)
	{
		try {
			
			incomeService.createIncome(income);
			redirectAttributes.addFlashAttribute("successMessage", "Income created successfully !");
			
		}catch(Exception e)
		{
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return null;
		
	}
}
