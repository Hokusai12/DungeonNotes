package com.ianhearne.dungeonnotes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.Character;
import com.ianhearne.dungeonnotes.models.ClassLevels;
import com.ianhearne.dungeonnotes.models.DndClass;
import com.ianhearne.dungeonnotes.repositories.ClassLevelsRepository;

@Service
public class ClassLevelsService {
	
	@Autowired
	ClassLevelsRepository repo;
	
	public ClassLevels saveNew(ClassLevels level) {
		return repo.save(level);
	}
	
	public ClassLevels saveNew(Character character, DndClass dndClass, Integer levels) {
		
		ClassLevels classLevels = new ClassLevels();
		
		classLevels.setCharacter(character);
		classLevels.setDndClass(dndClass);
		classLevels.setLevels(levels);
		
		return repo.save(classLevels);
	}
}
