package net.edigest.journalApp.Repositary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.repositary.UserRepositaryImpl;

@SpringBootTest
public class UserRepositaryImplTest {

    @Autowired
    private UserRepositaryImpl userRepositaryImpl;

    @Test
    public void getUserforSATest() {

        // Calling method
        List<User> users = userRepositaryImpl.getUserforSA();

        // Checking result is not null
        assertNotNull(users);

        // Checking list is not empty
        assertFalse(users.isEmpty());

        // Printing users for debugging
        users.forEach(user -> {
            System.out.println(user.getUsername());
            System.out.println(user.getEmail());
            System.out.println(user.isSentimentAnalysis());
            System.out.println("----------------------");
        });
    }
}