package com.ianhearne.dungeonnotes.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ianhearne.dungeonnotes.models.World;
import com.ianhearne.dungeonnotes.services.WorldService;

@Controller
@RequestMapping("/world")
public class WorldController {
	
	@Autowired
	WorldService worldService;
	
	////	GET MAPPINGS    ////
	
	@GetMapping("/add")
	public String showWorldCreation(Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		model.addAttribute("newWorld", new World());
		model.addAttribute("userId", (Long) session.getAttribute("userId"));
		
		return "world_templates/add_world.jsp";
	}
	
	@GetMapping("/{id}")
	public String showWorld(@PathVariable(name="id") Long worldId, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		session.setAttribute("worldId", worldId);
		
		model.addAttribute("userId", (Long) session.getAttribute("userId"));
		model.addAttribute("world", worldService.findById(worldId));
		
		return "world_templates/show_world.jsp";
	}
	
	@GetMapping("/{id}/edit")
	public String editWorld(@PathVariable(name="id") Long worldId, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		World worldToEdit = worldService.findById(worldId);
		
		//Checks if the World's creator's id is not equal to the id in session
		//This keeps users who are logged in validly from editing worlds that don't belong to them
		if(!worldToEdit.getCreator().getId().equals((Long) session.getAttribute("userId"))) {
			return "redirect:/homepage";
		}

		model.addAttribute("world", worldToEdit);
		model.addAttribute("userId", (Long) session.getAttribute("userId"));
		
		return "world_templates/edit_world.jsp";
	}
	
	////	POST MAPPINGS    ////
	
	@PostMapping("/add")
	public String saveCreatedWorld(@Valid @ModelAttribute("newWorld") World newWorld, BindingResult result) {
		if(result.hasErrors()) {
			return "world_templates/add_world.jsp";
		}
		worldService.create(newWorld);
		return "redirect:/homepage";
	}
	
	////	PUT MAPPINGS    ////
	
	@PutMapping("/{id}/update")
	public String saveEditedWorld(@Valid @ModelAttribute("world") World editedWorld, BindingResult result) {
		if(result.hasErrors()) {
			return "world_templates/edit_world.jsp";
		}
		
		worldService.saveChanges(editedWorld);
		return "redirect:/world/" + editedWorld.getId().toString();
	}
	
	////	DELETE MAPPINGS    ////
	
	@DeleteMapping("/{id}/delete")
	public String deleteWorld(@PathVariable(name="id") Long worldId) {
		worldService.delete(worldId);
		return "redirect:/homepage";
	}
}
