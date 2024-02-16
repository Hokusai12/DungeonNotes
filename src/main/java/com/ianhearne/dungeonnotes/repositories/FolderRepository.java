package com.ianhearne.dungeonnotes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.Folder;

@Repository
public interface FolderRepository extends CrudRepository<Folder, Long>{

}
