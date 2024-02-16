package com.ianhearne.dungeonnotes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.repositories.FolderRepository;

@Service
public class FolderService {
	@Autowired
	FolderRepository folderRepo;
}
