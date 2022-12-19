package com.example.securityproject.test;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public String message(String name) {
        return name;
    }
}
