package com.ianhearne.dungeonnotes.controllers;

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

import com.ianhearne.dungeonnotes.models.Folder;
import com.ianhearne.dungeonnotes.models.User;
import com.ianhearne.dungeonnotes.models.World;
import com.ianhearne.dungeonnotes.services.FolderService;
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
	@Autowired
	FolderService folderService;
	
	@GetMapping("/{id}")
	public String viewWorld(
			@PathVariable(name="id") Long worldId,
			Model model,
			HttpSession session) {
		World currentWorld = worldService.getWorldById(worldId);
		model.addAttribute("world", currentWorld);
		model.addAttribute("newFolder", new Folder());
		
		//folderService.printFolderStructure(currentWorld.getRootFolder());
		
		
		return "world_templates/viewWorld";
	}
	
	@GetMapping("/new")
	public String newWorld(Model model, HttpSession session) {
		model.addAttribute("newWorld", new World());
		
		return "world_templates/newWorld";
	}
	
	@PostMapping("/new")
	public String saveWorld(
			@Valid @ModelAttribute("newWorld") World newWorld,
			BindingResult result,
			Model model,
			HttpSession session) {
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
	
	@PutMapping("/{id}/update")
	public String updateWorld(
			@Valid @ModelAttribute("world") World world,
			BindingResult result,
			@PathVariable(name="id") Long id,
			Model model,
			HttpSession session) {
		World dbWorld = worldService.getWorldById(id);
		if(result.hasErrors()) {
			model.addAttribute("world", dbWorld);
			model.addAttribute("newFolder", new Folder());
			return "world_templates/viewWorld";
		}
		
		dbWorld.setName(world.getName());
		
		worldService.updateWorld(dbWorld);
		
		return "redirect:/world/" + dbWorld.getId();
	}
	
	@PostMapping("{id}/add-folder")
	public String addFolder(
			@Valid @ModelAttribute("newFolder") Folder newFolder,
			BindingResult result,
			@PathVariable(name="id") Long id,
			Model model,
			HttpSession session) {
		if(result.hasErrors()) {
			model.addAttribute("world", worldService.getWorldById(id));
			return "world_templates/viewWorld";
		}
		
		Folder savedFolder = folderService.saveFolder(newFolder);
		
		if(savedFolder.getParentFolder() == null) {
			System.out.println("It's a root folder");
		}
		
		return "redirect:/world/" + id;
	}
	
	@DeleteMapping("{id}/delete")
	public String deleteWorld(
			@PathVariable(name="id") Long id,
			HttpSession session) {
		worldService.deleteById(id);
		
		return "redirect:/home";
	}
}
