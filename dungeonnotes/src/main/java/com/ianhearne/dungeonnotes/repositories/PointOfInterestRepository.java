package com.ianhearne.dungeonnotes.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.PointOfInterest;

@Repository
public interface PointOfInterestRepository extends CrudRepository<PointOfInterest, Long>{
	public List<PointOfInterest> findAll();
}
