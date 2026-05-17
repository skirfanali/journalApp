package net.edigest.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.repositary.UserRepositary;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepositary userRepositary;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

	public User saveEntry(User user) {
		return userRepositary.save(user);
	}

	public boolean saveNewUser(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepositary.save(user);
			return true;
		} catch (Exception e) {
			log.info("All is save");
			log.error("Error is occur for {}: ", user.getUsername(), e);
			log.warn("Warning system may crash");
			log.debug("Debugging");
			log.trace("Tracking the record");
			return false;
		}

	}

	public User saveAdmin(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER", "ADMIN"));
		return userRepositary.save(user);
	}

	public User updateUser(User existingUser, User newUser) {

		existingUser.setUsername(newUser.getUsername());

		if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {

			existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		}

		return userRepositary.save(existingUser);
	}

	public List<User> getAll() {
		return userRepositary.findAll();
	}

	public Optional<User> findById(ObjectId id) {
		return userRepositary.findById(id);
	}

	public void deleteById(ObjectId id) {
		userRepositary.deleteById(id);
	}

	public User findByusername(String username) {
		return userRepositary.findByUsername(username);
	}

}
