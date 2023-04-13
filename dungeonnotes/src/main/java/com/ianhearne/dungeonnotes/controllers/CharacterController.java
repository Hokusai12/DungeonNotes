package com.ianhearne.dungeonnotes.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ianhearne.dungeonnotes.models.Character;
import com.ianhearne.dungeonnotes.models.ClassLevels;
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
		
		
		model.addAttribute("user", userService.findById((Long) session.getAttribute("userId")));
		model.addAttribute("newCharacter", new Character());
		model.addAttribute("newLevels", new ClassLevels());
		
		return "character_templates/add_character.jsp";
	}
	
	@GetMapping("/character/add-class")
	public String addClassLevelsToCharacter(Model model, HttpSession session) {
		
		model.addAttribute("newCharacterId", (Long) session.getAttribute("newCharacterId"));
		model.addAttribute("classList", classService.getAll());
		model.addAttribute("newClassLevel", new ClassLevels());
		
		return "character_templates/add_character_class.jsp";
	}
	
	
	@PostMapping("/character/add")
	public String saveNewCharacter(@Valid @ModelAttribute("newCharacter") Character newCharacter, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "character_templates/add_character.jsp";
		}
		
		session.setAttribute("newCharacterId", characterService.saveNew(newCharacter).getId());
		
		return "redirect:/character/add-class";
		
	}
	
	@PostMapping("/character/add-class")
	public String saveNewCharacterWithClass(@Valid @ModelAttribute("newClassLevel") ClassLevels levels, BindingResult result, @ModelAttribute("newCharacter") Character newCharacter) {
		if(result.hasErrors()) {
			return "character_templates/add_character_class.jsp";
		}
		
		classLevelsService.saveNew(levels);
		
		return "redirect:/homepage";
	}
}
