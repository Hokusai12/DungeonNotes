package com.ianhearne.dungeonnotes.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ianhearne.dungeonnotes.models.LoginUser;
import com.ianhearne.dungeonnotes.models.User;
import com.ianhearne.dungeonnotes.models.World;
import com.ianhearne.dungeonnotes.models.Character;
import com.ianhearne.dungeonnotes.services.CharacterService;
import com.ianhearne.dungeonnotes.services.UserService;
import com.ianhearne.dungeonnotes.services.WorldService;

@Controller
public class MainController {
	@Autowired
	UserService userService;
	
	@Autowired
	WorldService worldService;
	
	@Autowired
	CharacterService characterService;
	
	
	/*****
	 * 
	 * 		Login, Registration, Homepage, and Logout routes
	 * 
	*****/
	
	@GetMapping("/")
	public String loginAndRegistration(Model model) {
		return "greet_page.jsp";
	}
	
	@GetMapping("/register")
	public String registrationForm(Model model) {
		model.addAttribute("newUser", new User());
		return "register.jsp";
	}
	
	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("newLogin", new LoginUser());
		return "login.jsp";
	}
	
	@PostMapping("/register")
	public String registerNewUser(
			@Valid @ModelAttribute("newUser") User newUser,
			BindingResult result,
			Model model,
			HttpSession session) {
		
		if(result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "/login.jsp";
		} else {
			User registeredUser = userService.register(newUser,  result);//Register user, returns null if any exceptions are met
			if(result.hasErrors()) {
				model.addAttribute("newLogin", new LoginUser());
				return "/login.jsp";
			}
			session.setAttribute("userId", registeredUser.getId());
			return "redirect:/homepage";
		}
	}
	
	@PostMapping("/login")
	public String loginUser(
			@Valid @ModelAttribute("newLogin") LoginUser newLogin,
			BindingResult result,
			Model model,
			HttpSession session) {
		if(result.hasErrors()) {
			model.addAttribute("newUser", new User());
			
			return "/login.jsp";
		} else {
			User currentUser = userService.login(newLogin, result);
			
			if(result.hasErrors()) {
				model.addAttribute("newUser", new User());
				return "/login.jsp";
			}
			session.setAttribute("userId", currentUser.getId());
			return "redirect:/homepage";
		}
	}
	
	
	@GetMapping("/homepage")
	public String showHomepage(Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		if(session.getAttribute("worldId") != null) {
			session.removeAttribute("worldId");
		}
		
		List<World> allWorlds = worldService.getAllSortedByDate();
		User user = userService.findById((Long) session.getAttribute("userId"));
		
		model.addAttribute("user", user);
		model.addAttribute("worldList", allWorlds);
		
		return "homepage.jsp";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	/*****
	 * 
	 *			User-specific routes
	 * 
	*****/
	
	//   /user/${userId}/worlds
	@GetMapping("/user/{id}/worlds")
	public String showUserWorlds(@PathVariable(name="id") Long id, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		List<World> userWorlds = worldService.getAllByCreatorId(id);
		User user = userService.findById((Long) session.getAttribute("userId"));
		
		model.addAttribute("userWorldList", userWorlds);
		model.addAttribute("user", user);
		
		return "user_templates/user_worlds.jsp";
	}
	
	@GetMapping("/user/{id}/characters")
	public String showUserCharacters(@PathVariable(name="id") Long id, Model model, HttpSession session) {
		List<Character> userCharacters = characterService.getAllByCreatorId(id);
		
		model.addAttribute("characterList", userCharacters);
		
		return "character_templates/user_character.jsp";
	}
}
