package net.edigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	public UserService userService;

	@GetMapping("/healthcheck")
	public String healthCheck() {
		return "ok";
	}

	@PostMapping("/create-user")
	public void addUser(@RequestBody User user) {
		userService.saveNewUser(user);
	}

}
