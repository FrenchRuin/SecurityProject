package com.example.securityproject.config;

import com.example.securityproject.user.entity.User;
import com.example.securityproject.user.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class DBInitForTest implements InitializingBean {

    @Autowired
    private UserService userService;



    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {

        if(!userService.findUser("user1").isPresent()){
            User user = userService.save(User.builder()
                    .email("user1")
                    .password("1111")
                    .enabled(true)
                    .build());
            userService.addAuthority(user.getUserId(), "ROLE_USER");
        }
        if(!userService.findUser("user2").isPresent()){
            User user = userService.save(User.builder()
                    .email("user2")
                    .password("1111")
                    .enabled(true)
                    .build());
            userService.addAuthority(user.getUserId(), "ROLE_USER");
        }
        if(!userService.findUser("admin").isPresent()){
            User user = userService.save(User.builder()
                    .email("admin")
                    .password("1111")
                    .enabled(true)
                    .build());
            userService.addAuthority(user.getUserId(), "ROLE_ADMIN");
        }


    }
}
