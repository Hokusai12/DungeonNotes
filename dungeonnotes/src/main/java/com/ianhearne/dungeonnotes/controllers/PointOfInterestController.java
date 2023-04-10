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

import com.ianhearne.dungeonnotes.models.PointOfInterest;
import com.ianhearne.dungeonnotes.services.PointOfInterestService;

@Controller
@RequestMapping("/poi")
public class PointOfInterestController {
	
	@Autowired
	PointOfInterestService poiService;
	
	////	GET MAPPINGS    ////
	
	@GetMapping("/add")
	public String addPOI(Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		if(session.getAttribute("worldId") == null) {
			return "redirect:/homepage";
		}
		
		model.addAttribute("worldId", (Long) session.getAttribute("worldId"));
		model.addAttribute("newPOI", new PointOfInterest());
		
		return "poi_templates/add_poi.jsp";
	}
	
	@GetMapping("/{id}")
	public String showPOI(@PathVariable(name="id") Long poiId, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		if(session.getAttribute("worldId") == null) {
			return "redirect:/homepage";
		}
		
		model.addAttribute("userId", (Long) session.getAttribute("userId"));
		model.addAttribute("worldId", (Long) session.getAttribute("worldId"));
		model.addAttribute("poi", poiService.getById(poiId));
		
		return "poi_templates/show_poi.jsp";
	}
	
	@GetMapping("/{id}/edit")
	public String editPOI(@PathVariable(name="id") Long poiId, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		if(session.getAttribute("worldId") == null) {
			return "redirect:/homepage";
		}
		
		model.addAttribute("worldId", (Long) session.getAttribute("worldId"));
		model.addAttribute("poi", poiService.getById(poiId));
		
		return "poi_templates/edit_poi.jsp";
	}
	////	POST MAPPINGS    ////
	
	@PostMapping("/add")
	public String savePOI(@Valid @ModelAttribute("newPOI") PointOfInterest newPOI, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "poi_templates/add_poi.jsp";
		}
		
		poiService.create(newPOI);
		return "redirect:/world/" + session.getAttribute("worldId").toString();
	}
	
	////	PUT MAPPINGS    ////
	
	@PutMapping("/{id}/update")
	public String updatePOI(@Valid @ModelAttribute("poi") PointOfInterest poi, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "poi_templates/edit_poi.jsp";
		}
		
		poiService.update(poi);
		return "redirect:/poi/" + poi.getId();
	}
	
	////	DELETE MAPPINGS    ////
	
	@DeleteMapping("/{id}/delete")
	public String deletePOI(@PathVariable(name="id") Long poiId, HttpSession session) {
		poiService.delete(poiId);
		return "redirect:/world/" + session.getAttribute("worldId").toString();
	}
}
