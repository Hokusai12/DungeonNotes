package com.ianhearne.dungeonnotes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.World;

@Repository
public interface WorldRepository extends CrudRepository<World, Long>{
	@Query
	List<World> findByNameLike(String queryText);
	@Query
	List<World> findByNameStartingWith(String queryText);
	@Query
	List<World> findByNameEndingWith(String queryText);
	@Query
	List<World> findByNameContaining(String queryText);
	/*	Starting with ending with containing	*/
}
