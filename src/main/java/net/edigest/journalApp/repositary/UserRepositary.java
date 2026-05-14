package net.edigest.journalApp.repositary;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import net.edigest.journalApp.entity.User;

public interface UserRepositary extends MongoRepository<User, ObjectId> {
	
	User findByUsername(String username);

	void deleteByUsername(String username);
}
