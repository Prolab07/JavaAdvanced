package com.advanced.spring.it;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerIntegrationTest {

    //HTTP Basic Authentication додає автоматом якщо в конструктор їх передати
    //private final TestRestTemplate restTemplate = new TestRestTemplate("serhii", "serhii@123");
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @Test
    public void testGetUser() {
        String url = "http://localhost:" + "8080" + "/api/v1/users/info";

        // Створюємо заголовок для Basic Auth
        String auth = "serhii:serhii@123";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String authHeader = "Basic " + encodedAuth;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello user", response.getBody());
    }
}
