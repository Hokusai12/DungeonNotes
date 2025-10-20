package com.ianhearne.dungeonnotes.services;

import java.util.ArrayList;
import java.util.List;
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
	
	public boolean worldHasFolder(World world, Folder folder) {		
		return folderHasFolder(world.getRootFolder(), folder);
	}
	
	public boolean folderHasFolder(Folder rootFolder, Folder folder) {
		for(Folder checkFolder : rootFolder.getChildFolders()) {
			if(checkFolder.getId() == folder.getId()) {
				return true;
			}
			else {
				if(folderHasFolder(checkFolder, folder)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<World> searchWorlds(String queryText) {
		List<World> searchResults = worldRepo.findByNameLike(queryText);
		searchResults.addAll(worldRepo.findByNameStartingWith(queryText));
		searchResults.addAll(worldRepo.findByNameEndingWith(queryText));
		searchResults.addAll(worldRepo.findByNameContaining(queryText));
		
		List<World> cleanedSearchResults = new ArrayList<World>();
		
		for(World world : searchResults) {
			if(!cleanedSearchResults.contains(world)) {
				cleanedSearchResults.add(world);
			}
		}
		
		return cleanedSearchResults;
	}
}
