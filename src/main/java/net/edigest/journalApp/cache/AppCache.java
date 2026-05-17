package net.edigest.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import net.edigest.journalApp.entity.ConfigJournalAppEntity;
import net.edigest.journalApp.repositary.ConfigJournalAppRepo;

@Component
public class AppCache {

	public Map<String, String> storingKeyValue;

	@Autowired
	private ConfigJournalAppRepo configJournalAppRepo;

	@PostConstruct
	public void init() {

		storingKeyValue = new HashMap<>();
		List<ConfigJournalAppEntity> allPairs = configJournalAppRepo.findAll();

		for (ConfigJournalAppEntity configJournalApp : allPairs) {
			storingKeyValue.put(configJournalApp.getKey(), configJournalApp.getValue());
		}
	}
}