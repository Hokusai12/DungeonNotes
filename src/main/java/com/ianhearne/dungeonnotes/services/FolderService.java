package com.ianhearne.dungeonnotes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.Folder;
import com.ianhearne.dungeonnotes.repositories.FolderRepository;

@Service
public class FolderService {
	@Autowired
	FolderRepository folderRepo;
	
	public void printFolderStructure(Folder rootFolder) {
		
		List<Folder> currentFolders = rootFolder.getChildFolders();
		
		
		System.out.println(rootFolder.getName());
		
		for(int i = 0; i < currentFolders.size(); i++) {
			printFolderStructure(currentFolders.get(i));
		}
		
		for(int j = 0; j < rootFolder.getArticles().size(); j++) {
			System.out.println(rootFolder.getArticles().get(j).getTitle());
		}
	}
	
	public Folder saveFolder(Folder newFolder) {
		return folderRepo.save(newFolder);
	}
}
