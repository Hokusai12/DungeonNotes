package com.ianhearne.dungeonnotes.services;

import java.util.List;

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
}
