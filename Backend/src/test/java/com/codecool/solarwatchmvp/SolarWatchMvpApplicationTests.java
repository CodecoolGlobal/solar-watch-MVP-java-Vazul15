package com.codecool.solarwatchmvp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SolarWatchMvpApplicationTests {

    @LocalServerPort
    private int port;

    private MockWebServer mockWebServer;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetCurrentWithAuthentication() throws Exception {
        // Mock válasz beállítása regisztrációhoz
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"token\":\"mockedToken\"}")
                .addHeader("Content-Type", "application/json"));

        // Felhasználó regisztráció
        String registrationUrl = "http://localhost:" + port + "/api/user/register";
        String registrationRequestBody = "{\"email\":\"testuser@example.com\",\"password\":\"testpass\"}";

        // Megfelelő Content-Type fejléc beállítása
        HttpHeaders registrationHeaders = new HttpHeaders();
        registrationHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> registrationEntity = new HttpEntity<>(registrationRequestBody, registrationHeaders);

        ResponseEntity<String> registrationResponse = restTemplate.exchange(registrationUrl, HttpMethod.POST, registrationEntity, String.class);
        assertEquals(HttpStatus.OK, registrationResponse.getStatusCode());

        // Bejelentkezés
        String signinUrl = "http://localhost:" + port + "/api/user/signin";
        String signinRequestBody = "{\"email\":\"testuser@example.com\",\"password\":\"testpass\"}";

        // Beállítjuk a megfelelő Content-Type fejlécet
        HttpHeaders signinHeaders = new HttpHeaders();
        signinHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> signinEntity = new HttpEntity<>(signinRequestBody, signinHeaders);

        ResponseEntity<String> signinResponse = restTemplate.exchange(signinUrl, HttpMethod.POST, signinEntity, String.class);
        assertEquals(HttpStatus.OK, signinResponse.getStatusCode());

        // Token kinyerése JSON-ból
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(signinResponse.getBody());
        String token = rootNode.get("jwt").asText();

        // Autentikált kérés az /api/current végponthoz
        String currentUrl = "http://localhost:" + port + "/api/current?cityName=Budapest&countryCode=HU";
        HttpHeaders currentHeaders = new HttpHeaders();
        currentHeaders.set("Authorization", "Bearer " + token);
        HttpEntity<String> currentEntity = new HttpEntity<>(currentHeaders);

        ResponseEntity<String> currentResponse = restTemplate.exchange(currentUrl, HttpMethod.GET, currentEntity, String.class);

        // Ellenőrzés
        assertEquals(HttpStatus.OK, currentResponse.getStatusCode());
        String expectedBody = "{\"results\":{\"sunrise\":\"2024-09-01T04:01:43Z\",\"sunset\":\"2024-09-01T17:25:40Z\"}}";
        assertEquals(expectedBody, currentResponse.getBody());
    }

    @Test
    void testRegistrationSuccess() throws Exception {
        String registrationUrl = "http://localhost:" + port + "/api/user/register";
        String registrationRequestBody = "{\"email\":\"testuser@example.com\",\"password\":\"testpass\"}";

        HttpHeaders registrationHeaders = new HttpHeaders();
        registrationHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> registrationEntity = new HttpEntity<>(registrationRequestBody, registrationHeaders);

        ResponseEntity<String> registrationResponse = restTemplate.exchange(registrationUrl, HttpMethod.POST, registrationEntity, String.class);
        assertEquals(HttpStatus.OK, registrationResponse.getStatusCode());

    }

    @Test
    void loginSuccess() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"token\":\"mockedToken\"}")
                .addHeader("Content-Type", "application/json"));

        String registrationUrl = "http://localhost:" + port + "/api/user/register";
        String registrationRequestBody = "{\"email\":\"testuser@example.com\",\"password\":\"testpass\"}";

        HttpHeaders registrationHeaders = new HttpHeaders();
        registrationHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> registrationEntity = new HttpEntity<>(registrationRequestBody, registrationHeaders);

        ResponseEntity<String> registrationResponse = restTemplate.exchange(registrationUrl, HttpMethod.POST, registrationEntity, String.class);
        assertEquals(HttpStatus.OK, registrationResponse.getStatusCode());

        String signinUrl = "http://localhost:" + port + "/api/user/signin";
        String signinRequestBody = "{\"email\":\"testuser@example.com\",\"password\":\"testpass\"}";

        HttpHeaders signinHeaders = new HttpHeaders();
        signinHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> signinEntity = new HttpEntity<>(signinRequestBody, signinHeaders);

        ResponseEntity<String> signinResponse = restTemplate.exchange(signinUrl, HttpMethod.POST, signinEntity, String.class);
        assertEquals(HttpStatus.OK, signinResponse.getStatusCode());
    }
}
