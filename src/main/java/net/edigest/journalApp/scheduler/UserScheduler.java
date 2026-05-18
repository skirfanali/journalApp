package net.edigest.journalApp.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.edigest.journalApp.cache.AppCache;
import net.edigest.journalApp.entity.JournalEntry;
import net.edigest.journalApp.emuns.Sentiment;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.model.SentimentData;
import net.edigest.journalApp.repositary.UserRepositaryImpl;
import net.edigest.journalApp.service.EmailService;

@Component
public class UserScheduler {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepositaryImpl userRepositaryImpl;

	@Autowired
	private AppCache appCache;
	@Autowired
	private KafkaTemplate<String, SentimentData> kafkaTemplate;

	// Runs every minute
//	@Scheduled(cron = "0 * * ? * *")
	@Scheduled(fixedRate = 10000)
	public void fetchUsersAndSendSaMail() {

		List<User> users = userRepositaryImpl.getUserforSA();

		for (User user : users) {

			List<JournalEntry> journalEntries = user.getJournalEntries();

			List<Sentiment> sentiments = journalEntries.stream()

					.filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))

					.map(x -> x.getSentiment())

					.collect(Collectors.toList());

			Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

			for (Sentiment sentiment : sentiments) {

				if (sentiment != null) {

					sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
				}
			}

			Sentiment mostFrequentSentiment = null;

			int maxCount = 0;

			for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {

				if (entry.getValue() > maxCount) {

					maxCount = entry.getValue();

					mostFrequentSentiment = entry.getKey();
				}
			}

			if (mostFrequentSentiment != null) {
				SentimentData sentimentData = SentimentData.builder().email(user.getEmail())
						.sentiment("Sentiment for last 7 days: " + mostFrequentSentiment).build();
				System.out.println("Received from Kafka: " + sentimentData);
				kafkaTemplate.send("weekly-sentiment", sentimentData).whenComplete((result, ex) -> {

					if (ex == null) {
						System.out.println("Message Sent Successfully");
					} else {
						System.out.println("Error: " + ex.getMessage());
					}
				});

			}
		}
	}

	// Clear cache every 10 minutes
	@Scheduled(cron = "0 0/10 * * * ?")
	public void clearAppCache() {

		appCache.init();
	}
}