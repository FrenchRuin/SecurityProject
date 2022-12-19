package com.example.securityproject.test;

import org.springframework.stereotype.Component;

@Component
public class NameCheck {

    public boolean check(String name) {
        return name.equals("jaechan");
    }
}
