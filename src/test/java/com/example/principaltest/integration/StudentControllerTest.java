package com.example.principaltest.integration;

import com.example.principaltest.models.ApiPostRequest;
import com.example.principaltest.models.ApiResponse;
import com.example.principaltest.models.PostPerson;
import com.example.principaltest.models.PostStudent;
import com.example.principaltest.services.JpaUserDetailsService;
import com.example.principaltest.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@ActiveProfiles("inttest")
class StudentControllerTest {

    @Container
    @ServiceConnection
    static final MySQLContainer<?> database  = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.35"));

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    TokenService tokenService;
    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setup() {
        UserDetails principal = jpaUserDetailsService.loadUserByUsername("SYSADMIN");
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        String token = tokenService.generateToken(authentication);
        headers.set("Authorization", "Bearer " + token);
    }

    @Test
    void connectionEstablished() {
        assertThat(database.isCreated()).isTrue();
        assertThat(database.isRunning()).isTrue();
    }

    @Test
    void shouldFindAll() {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ApiResponse> response = restTemplate.exchange("/students", HttpMethod.GET, requestEntity, ApiResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindAll_UserUnauthorized() {
        UserDetails principal = jpaUserDetailsService.loadUserByUsername("SYSTEACHER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        String token = tokenService.generateToken(authentication);
        HttpHeaders teacherHeader= new HttpHeaders();
        teacherHeader.set("Authorization", "Bearer " + token);

        HttpEntity<String> requestEntity = new HttpEntity<>(teacherHeader);

        ResponseEntity<ApiResponse> response = restTemplate.exchange("/students", HttpMethod.GET, requestEntity, ApiResponse.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldFindById() {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ApiResponse> response = restTemplate.exchange("/students/{id}", HttpMethod.GET, requestEntity, ApiResponse.class, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Rollback
    void shouldCreateNewStudent() {
        PostPerson person = new PostPerson("name", "lastname", "123456789", "test@test.com", LocalDate.now(), "M");
        PostStudent student = new PostStudent(1L, person);
        ApiPostRequest request = new ApiPostRequest(student);
        HttpEntity<ApiPostRequest> requestEntity = new HttpEntity<>(request, headers);

        URI response = restTemplate.postForLocation("/students", requestEntity, ApiResponse.class);

        assertThat(response).isNotNull();
    }

    @Test
    @Rollback
    @WithMockCustomUser
    void shouldCreateNewStudent_WithContext() {
        // This test fail, according to the trace no bearer token is present

        //TRACE 4868 --- [PrincipalTest] [o-auto-1-exec-1] o.s.security.web.FilterChainProxy        : Invoking BearerTokenAuthenticationFilter (7/14)
        //TRACE 4868 --- [PrincipalTest] [o-auto-1-exec-1] .s.r.w.a.BearerTokenAuthenticationFilter : Did not process request since did not find bearer token

        PostPerson person = new PostPerson("name", "lastname", "123456789", "test@test.com", LocalDate.now(), "M");
        PostStudent student = new PostStudent(1L, person);
        ApiPostRequest request = new ApiPostRequest(student);

        // Not using headers, should use authentication created on WithMockCustomUserSecurityContextFactory
        URI response = restTemplate.postForLocation("/students", request, ApiResponse.class);

        assertThat(response).isNotNull();
    }
}