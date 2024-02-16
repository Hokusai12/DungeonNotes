package com.ianhearne.dungeonnotes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.Article;
import com.ianhearne.dungeonnotes.repositories.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	ArticleRepository articleRepo;
	
	public Article saveArticle(Article article) {
		return articleRepo.save(article);
	}
	
	public Article getArticleById(Long id) {
		Optional<Article> checkArticle = articleRepo.getArticleById(id);
		
		if(checkArticle.isPresent()) {
			return checkArticle.get();
		}
		
		return null;
	}
	
	public void deleteArticleById(Long id) {
		Article deleteArticle = getArticleById(id);
		if(deleteArticle == null) {
			return;
		}
		
		articleRepo.delete(deleteArticle);
	}
}
