package com.sayali.smart_expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sayali.smart_expense_tracker.security.JwtService;
import com.sayali.smart_expense_tracker.service.LoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@GetMapping("/login")
	public String showLoginPage() {
	    return "user/login";
	}
	
	@GetMapping("/home")
	public String home() {
	    return "user/home";
	}
	
	@PostMapping("/getLogin")
	public String getLogin(@RequestParam("username") String username,@RequestParam("password") String password,Model model, 
			RedirectAttributes redirectAttributes, HttpServletResponse response)
	{
		try { 
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( username, password)); 
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
			String token = jwtService.generateToken(userDetails); 
			System.out.println("JWT Token: " +token); 
			
			 Cookie jwtCookie = new Cookie("jwt", token);
		        jwtCookie.setHttpOnly(true);
		        jwtCookie.setPath("/");
		        jwtCookie.setMaxAge(60 * 60); // 1 hour

		        response.addCookie(jwtCookie);

		        redirectAttributes.addFlashAttribute("successMessage", "User logged in successfully!");
		        
			return "redirect:/home"; 
			
		}catch (Exception e) 
		  { 
			model.addAttribute( "errorMessage", "Invalid username or password" ); 
			return "user/login"; 
		} 	
	}
	
	@PostMapping("/logout")
	public String logout(RedirectAttributes redirectAttributes) {

	    redirectAttributes.addFlashAttribute("successMessage", "You have been logged out successfully!");

	    return "redirect:/login";
	}
}
