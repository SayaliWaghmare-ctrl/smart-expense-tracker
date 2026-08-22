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
	public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes)
	{
		userService.createUser(user);
		
		redirectAttributes.addFlashAttribute(
	            "successMessage",
	            "User created successfully!"
	    );
		return "redirect:/users";
		
	}
	
	@GetMapping("/userlist")
	public String getAllUsers(Model model)
	{
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "user/user-list";
		
	}
	
	@GetMapping("/edit-user/{id}")
	public String editUser(@PathVariable Long id, Model model) {

	    User user = userService.getUserById(id);

	    model.addAttribute("user", user);

	    return "edit-user";
	}
	
	
	
	
	
	
	
}
