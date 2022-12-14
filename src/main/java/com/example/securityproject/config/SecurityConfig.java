package com.example.securityproject.config;

import com.example.securityproject.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Order(1) // For TestSecurityConfig
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    final AuthDetail authDetail;
    final UserService userService;
    final DataSource dataSource;


    public SecurityConfig(AuthDetail authDetail, UserService userService, DataSource dataSource) {
        this.authDetail = authDetail;
        this.userService = userService;
        this.dataSource = dataSource;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests(
                        request -> {
                            request.antMatchers("/login","/h2-console/**","/static/**").permitAll()
                                    .anyRequest().authenticated();
                        }
                ).formLogin(
                        login -> {
                            login.loginPage("/login")
                                    .successHandler(new LoginSuccessHandler())
                                    .failureHandler(new LoginFailureHandler())
                                    .authenticationDetailsSource(authDetail);
                        }
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .exceptionHandling(e -> e.accessDeniedPage("/access-denied"))
                .userDetailsService(userService)
                .rememberMe(r -> r.key("rememberMe").userDetailsService(userService).tokenRepository(tokenRepository()))
                .build();
    }
/*
    Not recommend

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        );
    }
**/


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

}
