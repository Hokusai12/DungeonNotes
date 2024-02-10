package com.ianhearne.dungeonnotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ianhearne.dungeonnotes.models.User;
import com.ianhearne.dungeonnotes.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String landingPage() {
		return "landingPage";
	}
	
	@GetMapping("/register")
	public String homepage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String saveUser(
			@Valid @ModelAttribute("user") User user, 
			BindingResult result,
			Model model,
			HttpSession session) {
		
		if(result.hasErrors()) {
			return "register";
		}
		
		User savedUser = userService.saveUser(user,  result);
		
		if(result.hasErrors()) {
			return "register";
		}
		
		session.setAttribute("userId", savedUser.getId());
		return "redirect:/home";
	}
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login")
	public String checkLogin(
			@ModelAttribute("user") User user, 
			BindingResult result, 
			Model model, 
			HttpSession session) {
		
		User loginUser = userService.loginUser(user);
		
		if(loginUser == null) {
			result.rejectValue("email", "incorrectLogin", "Invalid Login");
			return "login";
		}
		
		session.setAttribute("userId", loginUser.getId());
		
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public String wallpage(Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/login";
		}
		
		Long userId = (Long)session.getAttribute("userId");
		User userInSession = userService.getUserById(userId);
		
		model.addAttribute("userInSession", userInSession);
		
		return "homepage";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
