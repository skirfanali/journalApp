package net.edigest.journalApp.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServicetest {

	@Autowired
	private EmailService emailService;

	@Test
	public void sendingTest() {
		emailService.sendEmail("rohitsk97490@gmail.com", "SMTP test",
				"Hey Rohit i am your biggest fan of all time and i love you like i don't know why i just wanna say you keep grow in your life like always may allah bless you"

		);

	}

}
