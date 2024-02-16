package com.ianhearne.dungeonnotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ianhearne.dungeonnotes.models.User;
import com.ianhearne.dungeonnotes.models.World;
import com.ianhearne.dungeonnotes.services.UserService;
import com.ianhearne.dungeonnotes.services.WorldService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/world")
public class WorldController {
	
	@Autowired
	WorldService worldService;
	@Autowired
	UserService userService;
	
	@GetMapping("/{id}")
	public String viewWorld(
			@PathVariable(name="id") Long worldId,
			Model model,
			HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		World currentWorld = worldService.getWorldById(worldId);
		model.addAttribute("world", currentWorld);
		
		return "world_templates/viewWorld";
	}
	
	@GetMapping("/new")
	public String newWorld(Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		model.addAttribute("newWorld", new World());
		
		return "world_templates/newWorld";
	}
	
	@PostMapping("/new")
	public String saveWorld(
			@Valid @ModelAttribute("newWorld") World newWorld,
			BindingResult result,
			Model model,
			HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		if(result.hasFieldErrors("name")) {
			return "world_templates/newWorld";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		User currentUser = userService.getUserById(userId);
		
		newWorld.setCreator(currentUser);
		World returnWorld = worldService.saveWorld(newWorld);
		
		if(returnWorld == null) {
			return "world_templates/newWorld";
		}
		
		return "redirect:/home";
	}
}
