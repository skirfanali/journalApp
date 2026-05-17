package net.edigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MultipleEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String[] to, String subject, String body) {

        try {

            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setFrom("irfanalisk110@gmail.com");

            mail.setTo(to);

            mail.setSubject(subject);

            mail.setText(body);

            javaMailSender.send(mail);

            System.out.println("Multiple Mail Sent Successfully");

        } catch (Exception e) {

            log.error("Exception: ", e);
        }
    }
}