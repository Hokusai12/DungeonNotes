package com.ianhearne.dungeonnotes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.Character;
import com.ianhearne.dungeonnotes.repositories.CharacterRepository;

@Service
public class CharacterService {
	
	@Autowired
	CharacterRepository repo;
	
	public List<Character> getAll() {
		return repo.findAll();
	}
	
	public Character findById(Long id) {
		Optional<Character> character = repo.findById(id);
		if(character.isPresent()) {
			return character.get();
		}
		return null;
	}
	
	public List<Character> getAllByCreatorId(Long id) {
		return repo.findByCreatorIdIs(id);
	}
	
	public Character saveNew(Character character) {
		return repo.save(character);
	}
	
	public Character update(Character character) {
		return repo.save(character);
	}
	
}
