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
			return "article_templates/createArticle";
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
			@ModelAttribute(name="article") Article editedArticle,
			BindingResult result,
			Model model,
			HttpSession session) {
		if(result.hasErrors()) {
			return "article_templates/editArticle";
		}
		
		
		
		articleService.saveArticle(editedArticle);
		
		
		return "redirect:/world?world-id="+worldId+"&article-id="+articleId;
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteArticle(@PathVariable(name="id") Long id, HttpSession session) {
		
		articleService.deleteArticleById(id);
		
		return "redirect:/home";
	}
}
