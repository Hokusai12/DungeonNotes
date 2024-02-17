package com.ianhearne.dungeonnotes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.Folder;
import com.ianhearne.dungeonnotes.models.World;
import com.ianhearne.dungeonnotes.repositories.WorldRepository;

@Service
public class WorldService {
	@Autowired
	WorldRepository worldRepo;
	@Autowired
	FolderService folderService;
	
	public World saveWorld(World newWorld) {
		Folder rootFolder = new Folder();
		rootFolder.setName(newWorld.getName());
		folderService.saveFolder(rootFolder);
		
		newWorld.setRootFolder(rootFolder);
	
		return worldRepo.save(newWorld);
	}
	
	public World updateWorld(World world) {
		return worldRepo.save(world);
	}
	
	public World getWorldById(Long id) {
		Optional<World> checkWorld = worldRepo.findById(id);
		if(checkWorld.isPresent()) {
			return checkWorld.get();
		}
		return null;
	}
	
	public void deleteById(Long id) {
		worldRepo.deleteById(id);
	}
}
