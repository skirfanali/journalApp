package net.edigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MultipleEmailServiceTest {

    @Autowired
    private MultipleEmailService multipleEmailService;

    @Test
    public void sendingTest() {

        String[] emails = {
                "rohitsk97490@gmail.com",
                "tanmoymaitit@gmail.com",
                
        };

        multipleEmailService.sendEmail(
                emails,
                "Spring Boot SMTP Test",
                "Hello everyone, this is a test email from Spring Boot."
        );
    }
}