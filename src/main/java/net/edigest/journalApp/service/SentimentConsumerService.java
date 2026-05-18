package net.edigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import net.edigest.journalApp.model.SentimentData;

@Service
public class SentimentConsumerService {
	@Autowired
	private EmailService emailService;

	private void sendEmail(SentimentData sentimentData) {
		emailService.sendEmail(sentimentData.getEmail(), "Sentiment for last 7 days", sentimentData.getSentiment());
	}

	@KafkaListener(topics = "weekly-sentiment", groupId = "weekly-sentiment-group")
	public void consume(SentimentData sentimentData) {
		System.out.println("Received from Kafka: " + sentimentData);
		sendEmail(sentimentData);
	}

}
