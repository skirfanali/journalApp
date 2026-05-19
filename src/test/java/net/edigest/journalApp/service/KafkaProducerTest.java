package net.edigest.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import net.edigest.journalApp.model.SentimentData;

@SpringBootTest
public class KafkaProducerTest {

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Disabled
    @Test
    public void sendMessageTest() {

        SentimentData sentimentData = SentimentData.builder()
                .email("rohitsk97490@gmail.com")
                .sentiment("HAPPY")
                .build();

        System.out.println("Sending to Kafka: " + sentimentData);

        kafkaTemplate.send(
                "weekly-sentiment",
                sentimentData.getEmail(),
                sentimentData
        );

        System.out.println("Message Sent Successfully");
    }
}