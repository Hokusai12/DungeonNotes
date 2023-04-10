package com.ianhearne.dungeonnotes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.World;
import com.ianhearne.dungeonnotes.repositories.WorldRepository;

@Service
public class WorldService {
	
	@Autowired
	WorldRepository repo;
	
	public List<World> getAll() {
		return repo.findAll();
	}
	
	public World findById(Long id) {
		Optional<World> world = repo.findById(id);
		if(world.isPresent()) {
			return world.get();
		}
		return null;
	}
	
	public List<World> getAllByCreatorId(Long creatorId) {
		return repo.findAllByCreatorIdIs(creatorId);
	}
	
	public void delete(Long worldId) {
		repo.deleteById(worldId);
	}
	
	public World create(World newWorld) {
		return repo.save(newWorld);
	}
	
	public World saveChanges(World editedWorld) {
		return repo.save(editedWorld);
	}
}
