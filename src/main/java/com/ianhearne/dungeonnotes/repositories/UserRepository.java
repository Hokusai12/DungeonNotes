package com.ianhearne.dungeonnotes.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ianhearne.dungeonnotes.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public Optional<User> getUserByEmail(String email);
	public Optional<User> getUserById(Long id);
}
