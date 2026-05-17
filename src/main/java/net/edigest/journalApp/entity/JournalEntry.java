package net.edigest.journalApp.entity;


import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.edigest.journalApp.emuns.Sentiment;


@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {

	@Id
	private ObjectId id;
	private String title;
	private String content;
	private LocalDateTime date;
	private Sentiment sentiment;

	public ObjectId getId() {
		return id;
	}

}
