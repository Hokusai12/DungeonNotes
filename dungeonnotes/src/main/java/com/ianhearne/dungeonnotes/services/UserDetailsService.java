package com.ianhearne.dungeonnotes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.UserRole;
import com.ianhearne.dungeonnotes.repositories.UserRoleRepository;

@Service
public class UserDetailsService {
	@Autowired
	UserRoleRepository repo;
	
	public UserRole findById(Long id) {
		Optional<UserRole> role = repo.findById(id);
		if(role.isPresent()) {
			return role.get();
		}
		return null;
	}
}
