package net.edigest.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import net.edigest.journalApp.cache.AppCache;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.service.UserService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final UserService userService;
	@Autowired
	private AppCache appCache;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUserFromDb() {

		List<User> allList = userService.getAll();

		if (allList != null && !allList.isEmpty()) {
			return new ResponseEntity<>(allList, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/create-admin")
	public ResponseEntity<String> creatingAdmin(@RequestBody User user) {

		userService.saveAdmin(user);

		return new ResponseEntity<>("Admin Created Successfully", HttpStatus.CREATED);
	}

	@GetMapping("/clearCache")
	public void clearCache() {
		appCache.init();
	}

}