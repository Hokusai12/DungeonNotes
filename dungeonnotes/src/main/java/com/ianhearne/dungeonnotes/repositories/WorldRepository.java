package com.ianhearne.dungeonnotes.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.World;

@Repository
public interface WorldRepository extends CrudRepository<World, Long>{
	public List<World> findAll();
	
	public List<World> findAllByCreatorIdIs(Long creatorId);
	
}
