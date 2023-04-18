package com.ianhearne.dungeonnotes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.DndClass;
import com.ianhearne.dungeonnotes.repositories.DndClassRepository;

@Service
public class DndClassService {
	
	@Autowired
	DndClassRepository classRepo;
	
	public List<DndClass> getAll() {
		return classRepo.findAll();
	}
	
	public DndClass getById(Long id) {
		Optional<DndClass> dndClass = classRepo.findById(id);
		if(dndClass.isPresent()) {
			return dndClass.get();
		}
		return null;
	}
}
