package com.sayali.smart_expense_tracker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sayali.smart_expense_tracker.entity.User;
import com.sayali.smart_expense_tracker.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/new")
	public String showCreateUser(Model model)
	{
		model.addAttribute("user", new User());
		
		return "user/create-user";
		
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute("user") User user,RedirectAttributes redirectAttributes)
	{
		userService.createUser(user);
		redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
		return "redirect:/users/userlist";
		
	}
	
	@GetMapping("/userlist")
	public String getAllUsers(Model model)
	{
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "user/user-list";
		
	}
	
	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable Long id, Model model) {

	    User user = userService.getUserById(id);
	    model.addAttribute("user", user);
	    return "user/edit-user";
	}
	
	@PostMapping("/editUser")
	public String getEditUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes)
	{
		userService.updateUser(user.getId(), user);
		redirectAttributes.addFlashAttribute("successMessage", "User updated successfully !");
		return "redirect:/users/userlist";
		
	}
	
	@PostMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {

	    userService.deleteUser(id);

	    redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
	    
	    return "redirect:/users/userlist";
	    	    
	}
	
	@GetMapping("/resetPassword")
	public String resetPasswordForm()
    {
    	return "user/reset-password";
    }
	
	@GetMapping("/forgot-password")
	public String showForgotPasswordPage(Model model) {

	    model.addAttribute("user", new User());

	    return "user/reset-password";
	}
	
	@PostMapping("/forgot-password")
	public String forgotPassword(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes)
	{
		 try {

		        userService.forgotPassword(user.getEmail());

		        redirectAttributes.addFlashAttribute("successMessage", "Password reset link has been sent to your email.");

		    } catch (RuntimeException e) {

		        redirectAttributes.addFlashAttribute("errorMessage","Email is not registered.");
		    }

		    return "redirect:/users/forgot-password";			
	}
	
	@GetMapping("/forgot-password-form")
	public String forgotPasswordForm( @RequestParam("token") String token, Model model)
	{

		if(!userService.validateResetToken(token))
		{
			model.addAttribute("errorMessage", "Invalid or expired password reset link");
			
			return "user/forgot-password";
		}
		
	   model.addAttribute("token", token);

	    return "user/forgot-password";
		
	}
	
		
	@PostMapping("/reset-password-form")
	public String resetPassword(@RequestParam("token") String token,@RequestParam("newPassword") String newPassword,
	        @RequestParam("confirmPassword") String confirmPassword,RedirectAttributes redirectAttributes) {

	    try {

	        userService.resetPassword(token, newPassword, confirmPassword);

	        redirectAttributes.addFlashAttribute("successMessage", "Password reset successfully. Please login.");

	        return "redirect:/login";

	    } catch (RuntimeException e) {

	        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

	        return "redirect:/users/forgot-password-form?token=" + token;
	    }
	}
	
}
