//package net.edigest.journalApp.service;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import net.edigest.journalApp.repositary.UserRepositary;
//
//@SpringBootTest
//public class UserServiceTest {
//
//	@Autowired
//	private UserRepositary userRepositary;
//
//	
//	@BeforeAll
//	void setUp() {
//		
//	}
//	@AfterAll
//	void afterRunningAll() {
//		
//	}
//	
//	@ParameterizedTest
//	@CsvSource({ "ram", "new", "rohit", "admin" })
//	public void testFindByUsername(String name) {
//
//		assertNotNull(userRepositary.findByUsername(name), "faild for: " + name);
//
//	}
//}