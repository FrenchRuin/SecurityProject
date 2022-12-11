package com.example.securityproject.config;

import com.example.securityproject.user.entity.User;
import com.example.securityproject.user.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DBInitForTest implements InitializingBean {

    private final UserService userService;

    public DBInitForTest(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(userService.findUser("user").isEmpty()){
            User user = userService.save(User.builder()
                    .email("user")
                    .password(passwordEncoder.encode("1111"))
                    .enabled(true)
                    .build());
            userService.addAuthority(user.getUserId(), "ROLE_USER");
        }
        if(userService.findUser("admin").isEmpty()){
            User user = userService.save(User.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("1111"))
                    .enabled(true)
                    .build());
            userService.addAuthority(user.getUserId(), "ROLE_ADMIN");
        }


    }
}
