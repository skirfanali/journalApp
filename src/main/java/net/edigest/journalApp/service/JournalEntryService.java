package net.edigest.journalApp.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.edigest.journalApp.entity.JournalEntry;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.repositary.JournalEntryRepositary;

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepositary journalEntryRepositary;
	@Autowired
	private UserService userService;

	@Transactional
	public void saveEntry(JournalEntry journalEntry, String username) {
		try {
			User user = userService.findByusername(username);
			JournalEntry saved = journalEntryRepositary.save(journalEntry);
			user.getJournalEntries().add(saved);

			userService.saveEntry(user);
		} catch (Error e) {
			throw new RuntimeErrorException(e);
		}
	}

	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepositary.save(journalEntry);

	}

	public List<JournalEntry> getAll() {
		return journalEntryRepositary.findAll();
	}

	public Optional<JournalEntry> findById(ObjectId id) {
		return journalEntryRepositary.findById(id);
	}

	public boolean deleteById(ObjectId id, String username) {
		boolean removed = false;
		try {
			User user = userService.findByusername(username);
			removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
			if (removed) {
				userService.saveEntry(user);
				journalEntryRepositary.deleteById(id);
				
			}

		} catch (Exception e) {
			throw new RuntimeErrorException(null, "An error occurred during deleting the entry:" + e);
		}
		return removed;

	}

	public List<JournalEntry> findbyUsername(String username) {
		return null;
	}

}
