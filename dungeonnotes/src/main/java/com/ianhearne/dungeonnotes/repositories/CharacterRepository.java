package com.ianhearne.dungeonnotes.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.Character;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Long>{
	public List<Character> findAll();
	
	public List<Character> findByCreatorIdIs(Long id);
}
