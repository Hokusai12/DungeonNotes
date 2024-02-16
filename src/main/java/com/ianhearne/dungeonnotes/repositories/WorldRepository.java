package com.ianhearne.dungeonnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.World;

@Repository
public interface WorldRepository extends CrudRepository<World, Long>{
	
}
