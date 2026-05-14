package net.edigest.journalApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.edigest.journalApp.entity.JournalEntry;


@RestController
@RequestMapping("/_journal")
public class RestApi {
	private Map<Long, JournalEntry> journalEntries = new HashMap<Long, JournalEntry>();

	@GetMapping
	public List<JournalEntry> getAll() {
		return new ArrayList<>(journalEntries.values());
	}

	@PostMapping
	public boolean createEntry(@RequestBody JournalEntry myEntry) {
//		JournalEntry put = journalEntries.put((long) myEntry.getId(), myEntry);
		return true;
	}

	@GetMapping("id/{myId}")
	public JournalEntry getJournalEntryById(@PathVariable long myId) {
		return journalEntries.get(myId);
	}

	@DeleteMapping("id/{myId}")
	public JournalEntry deleteJournalEntryById(@PathVariable long myId) {
		return journalEntries.remove(myId);
	}
	
	@PutMapping("/id/{id}")
	public JournalEntry updatJournalEntryById(@PathVariable long id,@RequestBody JournalEntry myEntry) {
		return journalEntries.put(id, myEntry);
	}
	
	
}
