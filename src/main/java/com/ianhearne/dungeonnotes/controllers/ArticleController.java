package com.ianhearne.dungeonnotes.controllers;

import java.util.HashMap;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.ianhearne.dungeonnotes.models.Article;
import com.ianhearne.dungeonnotes.services.ArticleService;
import com.ianhearne.dungeonnotes.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/world/article")
public class ArticleController {
	@Autowired
	ArticleService articleService;
	@Autowired
	UserService userService;
	
	@GetMapping("/new")
	public String newArticle(Model model, HttpSession session) {
		model.addAttribute("newArticle", new Article());
		
		return "article_templates/createArticle";
	}
	
	@PostMapping("/new")
	public String saveNewArticle(
			@Valid @ModelAttribute(name="newArticle") Article newArticle,
			@RequestParam(name="world-id") Long worldId,
			BindingResult result,
			Model model,
			HttpSession session) {
		if(result.hasErrors()) {
			return "world?world-id=" + worldId;
		}
		
		if(newArticle.getFolder().getWorld() != null) {
			return "world?world-id=" + worldId;
		}
		
		
		Article savedArticle = articleService.saveArticle(newArticle);
		
		return "redirect:/world?world-id="+worldId+"&article-id="+savedArticle.getId();
	}
	
	@GetMapping("/edit/{id}")
	public String editArticle(
			@PathVariable(name="id") Long articleId,
			Model model,
			HttpSession session) {
		Article currentArticle = articleService.getArticleById(articleId);
		if(currentArticle == null) {
			return "redirect:/home";
		}
		
		model.addAttribute("article", currentArticle);
		
		
		return "article_templates/editArticle";
	}
	
	@PutMapping("/update")
	public String saveEditsToArticle(
			@RequestParam(name="world-id") Long worldId,
			@RequestParam(name="article-id") Long articleId,
			@ModelAttribute(name="selectedArticle") Article selectedArticle,
			BindingResult result,
			Model model,
			HttpSession session) {
			
		Article dbArticle = articleService.getArticleById(articleId);
		dbArticle.setText(selectedArticle.getText());
		dbArticle.setTitle(selectedArticle.getTitle());
		
		articleService.saveArticle(dbArticle);
		
		return "redirect:/world?world-id="+worldId+"&article-id="+articleId;
	}
	
	@PutMapping("/update-w-form")
	public String saveArticleTitle(
			@RequestParam(name="world-id") Long worldId,
			@RequestParam(name="article-id") Long articleId,
			@RequestParam HashMap<String, String> formData) {
		
		Article dbArticle = articleService.getArticleById(articleId);
		dbArticle.setTitle(formData.get("name"));
		
		articleService.saveArticle(dbArticle);
		
		return "redirect:/world?world-id="+worldId+"&article-id="+articleId;
		
	}
	
	@DeleteMapping("/delete")
	public String deleteArticle(@PathVariable(name="id") Long id, HttpSession session) {
		
		articleService.deleteArticleById(id);
		
		return "redirect:/home";
	}
}
