package com.ianhearne.dungeonnotes.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
	Optional<Article> getArticleById(Long id);
}
