package net.edigest.journalApp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.edigest.journalApp.entity.JournalEntry;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.service.JournalEntryService;
import net.edigest.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

	@Autowired
	private JournalEntryService journalEntryService;
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getAllJournalEntryOfUser() {

		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		String username = authentication.getName();

		User user = userService.findByusername(username);

		List<JournalEntry> allEntries = user.getJournalEntries();
		if (allEntries != null && !allEntries.isEmpty()) {
			return new ResponseEntity<>(allEntries, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {

		try {
			org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();

			String username = authentication.getName();
			myEntry.setDate(LocalDateTime.now());
			journalEntryService.saveEntry(myEntry, username);
			return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId) {

		try {

			org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();

			String username = authentication.getName();

			User user = userService.findByusername(username);

			ObjectId objectId = new ObjectId(myId);

			List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(objectId))
					.collect(Collectors.toList());

			if (!collect.isEmpty()) {

				Optional<JournalEntry> journalEntry = journalEntryService.findById(objectId);

				return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
						.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (IllegalArgumentException e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		String username = authentication.getName();
		boolean removed = journalEntryService.deleteById(myId, username);
		if (removed) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<JournalEntry> updateJournalById(@PathVariable String id, @RequestBody JournalEntry newEntry) {

		try {

			org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();

			String username = authentication.getName();

			User user = userService.findByusername(username);

			ObjectId objectId = new ObjectId(id);

			List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(objectId))
					.collect(Collectors.toList());

			if (collect.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			JournalEntry old = journalEntryService.findById(objectId).orElse(null);

			if (old != null) {

				old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
						: old.getTitle());

				old.setContent(
						newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
								: old.getContent());

				journalEntryService.saveEntry(old);

				return new ResponseEntity<>(old, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (IllegalArgumentException e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
