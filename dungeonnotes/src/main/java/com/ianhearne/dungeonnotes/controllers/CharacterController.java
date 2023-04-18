package com.ianhearne.dungeonnotes.controllers;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.ianhearne.dungeonnotes.models.Character;
import com.ianhearne.dungeonnotes.models.ClassLevels;
import com.ianhearne.dungeonnotes.models.DndClass;
import com.ianhearne.dungeonnotes.services.CharacterService;
import com.ianhearne.dungeonnotes.services.ClassLevelsService;
import com.ianhearne.dungeonnotes.services.DndClassService;
import com.ianhearne.dungeonnotes.services.UserService;

@Controller
public class CharacterController {
	@Autowired
	CharacterService characterService;
	
	@Autowired
	DndClassService classService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ClassLevelsService classLevelsService;
	
	@GetMapping("/character/add")
	public String addCharacterForm(Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		model.addAttribute("user", userService.findById((Long) session.getAttribute("userId")));
		model.addAttribute("newCharacter", new Character());
		model.addAttribute("newLevels", new ClassLevels());
		model.addAttribute("classList", classService.getAll());
		
		return "character_templates/add_character.jsp";
	}
	
	
	@PostMapping("/character/add")
	public String saveNewCharacter(@Valid @ModelAttribute("newCharacter") Character newCharacter, BindingResult result, Model model, HttpSession session, @RequestParam Map<String, String> allParams) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		if(result.hasErrors()) {
			model.addAttribute("user", userService.findById((Long) session.getAttribute("userId")));
			model.addAttribute("newCharacter", new Character());
			model.addAttribute("newLevels", new ClassLevels());
			model.addAttribute("classList", classService.getAll());
			
			return "character_templates/add_character.jsp";
		}
		

		characterService.saveNew(newCharacter);
		
		Long userId = (Long) session.getAttribute("userId");
		
		for(int i = 1; i <= allParams.size()/2; i++) {
			
			if(allParams.get("dnd-class-".concat(String.valueOf(i))) == null) {
				break;
			}
			
			Long classId = Long.parseLong(allParams.get("dnd-class-".concat(String.valueOf(i)))); 
			DndClass dndClass = classService.getById(classId);
			Integer levels = Integer.parseInt(allParams.get("class-levels-".concat(String.valueOf(i)))); 
		
			
			classLevelsService.saveNew(newCharacter, dndClass, levels);
		}
		
		
		return "redirect:/user/"+userId+"/characters";
	}
	
	@DeleteMapping("/character/{id}/delete")
	public String deleteCharacter(@PathVariable(name="id") Long id, HttpSession session) {
		characterService.deleteById(id);
		
		Long userId = (Long) session.getAttribute("userId");
		
		return "redirect:/user/"+userId+"/characters";
	}
}
