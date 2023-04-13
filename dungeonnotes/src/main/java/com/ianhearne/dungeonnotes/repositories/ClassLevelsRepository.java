package com.ianhearne.dungeonnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.ClassLevels;

@Repository
public interface ClassLevelsRepository extends CrudRepository<ClassLevels, Long>{
	
}
