package com.sayali.smart_expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sayali.smart_expense_tracker.service.LoginService;


@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@GetMapping("/login")
	public String showLoginPage() {
	    return "user/login";
	}
	
	@PostMapping("/getLogin")
	public String getLogin(@RequestParam("username") String username,@RequestParam("password") String password,Model model, RedirectAttributes redirectAttributes)
	{
		boolean isValid = loginService.validateLogin(username, password);
		
		if(isValid)
		{
			redirectAttributes.addFlashAttribute("successMessage", "User logged in successfully!");
			return "user/home";
		}
		
		model.addAttribute("errorMessage","Invalid user and password");
		
		
		return "user/login";
		
	}
}
