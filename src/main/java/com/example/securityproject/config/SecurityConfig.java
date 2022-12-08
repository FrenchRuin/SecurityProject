package com.example.securityproject.config;

import com.example.securityproject.user.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
    private RoleHierarchy roleHierarchy;

    final AuthDetail authDetail;
    final UserService userService;
    final DataSource dataSource;


    public SecurityConfig(AuthDetail authDetail, UserService userService, DataSource dataSource) {
        this.authDetail = authDetail;
        this.userService = userService;
        this.dataSource = dataSource;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf().disable()
                .authorizeRequests(
                        request -> {
                            request.antMatchers("/auth", "/login").permitAll()
                                    .anyRequest().authenticated();
                        }
                ).formLogin(
                        login -> {
                            login.loginPage("/login")
                                    .defaultSuccessUrl("/auth", false);
//                                    .authenticationDetailsSource(authDetail);
                        }
                )
//                .userDetailsService(userService)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations(),
                PathRequest.toH2Console()
        );
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("1111"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
