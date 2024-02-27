package com.example.principaltest.integration;

import com.example.principaltest.models.ApiPostRequest;
import com.example.principaltest.models.ApiResponse;
import com.example.principaltest.models.PostPerson;
import com.example.principaltest.models.PostStudent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Container
    @ServiceConnection
    static final MySQLContainer<?> database  = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.35"));

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Test
    void connectionEstablished() {
        assertThat(database.isCreated()).isTrue();
        assertThat(database.isRunning()).isTrue();
    }
    @Test
    void findAll() {
        ResponseEntity<ApiResponse> response = restTemplate
                .getForEntity("/students", ApiResponse.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @Rollback
    void shouldCreateNewPostWhenPostIsValid() {
        PostPerson person = new PostPerson("name", "lastname", "123456789", "test@test.com", LocalDate.now(), "M");
        PostStudent postEntity = new PostStudent(1L, person);
        ApiPostRequest apiPostRequest = new ApiPostRequest(postEntity);

        HttpEntity<ApiPostRequest> requestEntity = new HttpEntity<>(apiPostRequest);

        // How can I pass the UriComponentsBuilder and the Principal on the request?
        URI response = restTemplate
                .postForLocation("/students", requestEntity, ApiResponse.class);

        assertThat(response).isNotNull();
    }
}