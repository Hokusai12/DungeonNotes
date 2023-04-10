package com.ianhearne.dungeonnotes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.PointOfInterest;
import com.ianhearne.dungeonnotes.repositories.PointOfInterestRepository;

@Service
public class PointOfInterestService {
	@Autowired
	PointOfInterestRepository repo;
	
	public PointOfInterest create(PointOfInterest newPOI) {
		return repo.save(newPOI);
	}
	
	public PointOfInterest update(PointOfInterest poi) {
		return repo.save(poi);
	}
	
	public void delete(Long poiId) {
		repo.deleteById(poiId);
	}
	
	public PointOfInterest getById(Long id) {
		Optional<PointOfInterest> poi = repo.findById(id);
		if(poi.isPresent()) {
			return poi.get();
		}
		return null;
	}
	
}
