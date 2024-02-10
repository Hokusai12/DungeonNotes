package com.ianhearne.dungeonnotes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.UserAuthority;
import com.ianhearne.dungeonnotes.repositories.UserAuthorityRepository;

@Service
public class UserAuthorityService {
	
	@Autowired
	UserAuthorityRepository authRepo;
	
	public UserAuthority getAuthority(Long id) {
		Optional<UserAuthority> auth = authRepo.findById(id);
		if(auth.isPresent()) {
			return auth.get();
		}
		else {
			return null;
		}
	}
}
