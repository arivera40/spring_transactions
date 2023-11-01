package com.demo.spring_transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring_transaction.model.BankAccount;
import com.demo.spring_transaction.model.User;
import com.demo.spring_transaction.repository.UserRepository;

@RestController
public class UserController {

	private final UserRepository userRepository;
	
	public UserController(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PutMapping(path = "/users/{userId}")
	public ResponseEntity<User> createUpdateUser(
			@PathVariable final long userId,
			@RequestBody final User user) {
	
		user.setUserId(userId);
		boolean userExists = userRepository.existsById(userId);
		final User savedUser = userRepository.save(user);
		
		if (userExists) {
			return new ResponseEntity<User>(savedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		}
	}
}
