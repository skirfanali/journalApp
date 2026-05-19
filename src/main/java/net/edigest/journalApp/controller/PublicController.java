package net.edigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.service.UserDetailServiceImpl;
import net.edigest.journalApp.service.UserService;
import net.edigest.journalApp.utilies.Jwtutil;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	@Autowired
	private Jwtutil jwtutil;

	@GetMapping("/healthcheck")
	public String healthCheck() {
		return "ok";
	}

	@PostMapping("/signup")
	public void addUser(@RequestBody User user) {
		userService.saveNewUser(user);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getUsername());
			String jwtString = jwtutil.generateToken(userDetails.getUsername());
			return new ResponseEntity<>(jwtString, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exceptation occurs during token generation: ", e);
			return new ResponseEntity<>("Incorrect username and password ", HttpStatus.BAD_REQUEST);
		}
	}

}
