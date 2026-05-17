package net.edigest.journalApp.repositary;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.edigest.journalApp.entity.ConfigJournalAppEntity;

public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournalAppEntity, String> {

}
