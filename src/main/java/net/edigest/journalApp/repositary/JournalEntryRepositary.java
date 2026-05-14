package net.edigest.journalApp.repositary;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import net.edigest.journalApp.entity.JournalEntry;



public interface JournalEntryRepositary extends MongoRepository<JournalEntry, ObjectId> {

}
