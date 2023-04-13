package com.ianhearne.dungeonnotes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.ClassLevels;
import com.ianhearne.dungeonnotes.repositories.ClassLevelsRepository;

@Service
public class ClassLevelsService {
	
	@Autowired
	ClassLevelsRepository repo;
	
	public ClassLevels saveNew(ClassLevels level) {
		return repo.save(level);
	}
}
