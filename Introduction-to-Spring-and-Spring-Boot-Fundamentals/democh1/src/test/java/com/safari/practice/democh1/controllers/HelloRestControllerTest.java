package com.safari.practice.democh1.controllers;

import com.safari.practice.democh1.entity.Greeting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloRestControllerTest {
    
    @Autowired
    private TestRestTemplate template;

    @Test
    public void greetingWithoutName() {
        ResponseEntity<Greeting> responseEntity = template.getForEntity("/rest", Greeting.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());

        Greeting response = responseEntity.getBody();
        assertEquals("Hello World !", response.getMessage());
    }
}
