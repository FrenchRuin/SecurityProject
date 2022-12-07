package com.example.securityproject.config;

import com.example.securityproject.user.entity.User;
import com.example.securityproject.user.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class DBInitForTest implements InitializingBean {

    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(!userService.findUser("user1").isPresent()){
            User user = userService.save(User.builder()
                    .email("user1")
                    .password("1111")
                    .enabled(true)
                    .build());
            userService.addAuthority(user.getUserId(), "ROLE_USER");
        }
    }
}
