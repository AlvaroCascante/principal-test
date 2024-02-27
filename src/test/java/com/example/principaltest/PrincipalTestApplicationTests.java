package com.example.principaltest;

import com.example.principaltest.controllers.AuthController;
import com.example.principaltest.controllers.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PrincipalTestApplicationTests {

	@Autowired
	private AuthController authController;
	@Autowired
	private StudentController studentController;

	@Test
	void contextLoads() {
		assertThat(authController).isNotNull();
		assertThat(studentController).isNotNull();
	}

}
