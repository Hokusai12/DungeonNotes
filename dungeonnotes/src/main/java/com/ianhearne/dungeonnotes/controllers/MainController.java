package com.ianhearne.dungeonnotes.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ianhearne.dungeonnotes.models.Character;
import com.ianhearne.dungeonnotes.models.User;
import com.ianhearne.dungeonnotes.models.World;
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
	public String loginAndRegistration() {
		return "greet_page.jsp";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login.jsp";
	}
	
	@PostMapping("/login")
	public String login() {
		return "redirect:/homepage";
	}
	
	@GetMapping("/register")
	public String registrationForm(Model model) {
		model.addAttribute("newUser", new User());
		return "register.jsp";
	}

	@PostMapping("/register")
	public String registerNewUser(
			@Valid @ModelAttribute("newUser") User newUser,
			BindingResult result,
			Model model,
			HttpSession session) {
		
		if(result.hasErrors()) {
			return "/register.jsp";
		} else {
			User registeredUser = userService.register(newUser,  result);
			if(result.hasErrors()) {
				return "/register.jsp";
			}
			return "redirect:/homepage";
		}
	}	
	
	@GetMapping("/homepage")
	public String showHomepage(Model model, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		User user = userService.findByEmail(name);
		if(session.getAttribute("worldId") != null) {
			session.removeAttribute("worldId");
		}
		if(session.getAttribute("userId") == null) {
			session.setAttribute("userId", user.getId());
		}
		
		List<World> allWorlds = worldService.getAllSortedByDate();
		
		model.addAttribute("user", user);
		model.addAttribute("worldList", allWorlds);
		
		return "homepage.jsp";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping("/dungeonmaker")
	public String dungeonMaker() {
		return "dungeonmaker.jsp";
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
		if(!session.getAttribute("userId").equals(id)) {
			return "redirect:/homepage";
		}
		
		List<World> userWorlds = worldService.getAllByCreatorId(id);
		User user = userService.findById((Long) session.getAttribute("userId"));
		
		model.addAttribute("userWorldList", userWorlds);
		model.addAttribute("user", user);
		
		return "world_templates/user_worlds.jsp";
	}
	
	@GetMapping("/user/{id}/characters")
	public String showUserCharacters(@PathVariable(name="id") Long id, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		if(!session.getAttribute("userId").equals(id)) {
			return "redirect:/homepage";
		}
		List<Character> userCharacters = characterService.getAllByCreatorId(id);
		
		User user = userService.findById((Long) session.getAttribute("userId"));
		
		model.addAttribute("characterList", userCharacters);
		model.addAttribute("user", user);
		
		return "character_templates/user_character.jsp";
	}
}
