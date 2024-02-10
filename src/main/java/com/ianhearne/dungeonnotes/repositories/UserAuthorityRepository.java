package com.ianhearne.dungeonnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.UserAuthority;

@Repository
public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long>{
	
}
