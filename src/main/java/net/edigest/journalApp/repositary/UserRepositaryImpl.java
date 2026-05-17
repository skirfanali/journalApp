package net.edigest.journalApp.repositary;

import java.nio.channels.Pipe.SourceChannel;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import net.edigest.journalApp.entity.User;

//Interact with DB by using some criteria

public class UserRepositaryImpl {

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<User> getUserforSA() {
		Query query = new Query();
//		query.addCriteria(Criteria.where("username").is("new"));

		query.addCriteria(Criteria.where("email").exists(true).ne(null).ne(""));
		query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

		List<User> users = mongoTemplate.find(query, User.class);
		return users;
	}

}
