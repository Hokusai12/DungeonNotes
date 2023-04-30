package com.ianhearne.dungeonnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long>{
	
}
