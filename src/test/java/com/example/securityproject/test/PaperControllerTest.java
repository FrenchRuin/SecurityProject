package com.example.securityproject.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class PaperControllerTest extends TestSetting{

    TestRestTemplate client;

    @DisplayName("1. greeting 메세지 호출 ")
    @Test
    void test_1(){
        client = new TestRestTemplate("user1", "1111");
        ResponseEntity<String> response = client.getForEntity("/paper/greeting/jaechan", String.class);

        assertEquals("hello jaechan", response.getBody());
        System.out.println(response.getBody());
    }


}