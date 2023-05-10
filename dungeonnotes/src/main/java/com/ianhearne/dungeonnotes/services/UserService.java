package com.ianhearne.dungeonnotes.services;

import java.util.Arrays;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.ianhearne.dungeonnotes.models.User;
import com.ianhearne.dungeonnotes.models.UserRole;
import com.ianhearne.dungeonnotes.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserDetailsService userDetails;
	
	public User register(User newUser, BindingResult result) {
		////REGISTRATION VALIDATION////
		boolean isValid = true;
		Optional<User> checkUser = userRepo.findByEmail(newUser.getEmail());
		if(checkUser.isPresent()) { //Check if email is already in use
			result.rejectValue("email", "emailInUse", "That email is already in use");
			isValid = false;
		}
		if(!newUser.getUsername().matches("[a-zA-Z]+")) { //Username must be letters only
			result.rejectValue("userName", "usernameError", "Username must only have letters");
			isValid = false;
		}
		if(!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("confirm", "passwordsMatch", "Passwords must match!");
			isValid = false;
		}
		if(isValid) {
			String pwHash = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(pwHash);
			UserRole userRole = userDetails.findById(1L);
			newUser.setRoles(Arrays.asList(userRole));
			User savedUser = userRepo.save(newUser);
			return savedUser;
		} else {
			return null;
		}
	}
	
	public User findById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}
	
	public User findByEmail(String email) {
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}
}
