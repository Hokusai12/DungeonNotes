package com.ianhearne.dungeonnotes.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.ianhearne.dungeonnotes.models.User;
import com.ianhearne.dungeonnotes.models.UserAuthority;
import com.ianhearne.dungeonnotes.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserAuthorityService authService;
	
	public User saveUser(User user, BindingResult result) {
		boolean isValid = true;
		
		Optional<User> checkUser = userRepo.getUserByEmail(user.getEmail());
		
		//Check if the email is already in use
		if(checkUser.isPresent()) {
			result.rejectValue("email", "emailInUse", "Email is already in use");
			isValid = false;
		}
		
		//Check if the password and confirm password match
		if(!user.getPassword().equals(user.getConfirm())) {
			result.rejectValue("password", "passwordMismatch", "Passwords must match!");
			isValid = false;
		}
		
		if(!isValid) {
			return null;
		}
		
		//Hash the password and store that on the DB
		String pwHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(pwHash);
		
		//Set userAuthority to User Level
		UserAuthority userAuth = authService.getAuthority(Long.valueOf(2));
		user.setAuthority(userAuth);
		
		return userRepo.save(user);
	}
	
	public User loginUser(User user) {
		//the user only has an email and password attribute applied, nothing else
		
		//Check if email is already in DB
		Optional<User> checkUser = userRepo.getUserByEmail(user.getEmail());
		if(!checkUser.isPresent()) {
			return null;
		}
		
		User dbUser = checkUser.get();
		
		//Check user password matches the one stored in DB
		if(!BCrypt.checkpw(user.getPassword(), dbUser.getPassword())) {
			return null;
		}
		
		return dbUser;
	}
	
	public User getUserById(Long userId) {
		Optional<User> user = userRepo.getUserById(userId);
		
		if(!user.isPresent()) {
			return null;
		}
		
		return user.get();
	}
}
