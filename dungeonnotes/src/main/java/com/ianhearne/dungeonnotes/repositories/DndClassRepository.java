package com.ianhearne.dungeonnotes.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.DndClass;

@Repository
public interface DndClassRepository extends CrudRepository<DndClass, Long>{
	public List<DndClass> findAll();
}
