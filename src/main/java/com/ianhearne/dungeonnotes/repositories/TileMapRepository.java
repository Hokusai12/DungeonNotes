package com.ianhearne.dungeonnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.TileMap;

@Repository
public interface TileMapRepository extends CrudRepository<TileMap, Long> {

}
