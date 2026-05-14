package net.edigest.journalApp.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.repositary.UserRepositary;
import net.edigest.journalApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserService userService;
	@Autowired
	public UserRepositary userRepositary;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {

		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		String username = authentication.getName();
		User byUserName = userService.findByusername(username);

		if (byUserName != null) {
			userService.updateUser(byUserName, user);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteJournalEntryById() {
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		userRepositary.deleteByUsername(authentication.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
