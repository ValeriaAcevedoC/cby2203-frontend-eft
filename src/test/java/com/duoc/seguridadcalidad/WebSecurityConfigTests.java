package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WebSecurityConfigTests {

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Autowired(required = false)
    private AuthenticationManager authenticationManager;

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertNotNull(webSecurityConfig);
    }

    @Test
    void authenticationManagerBeanExists() {
        assertNotNull(authenticationManager);
    }

    @Test
    void userDetailsServiceBeanExists() {
        assertNotNull(userDetailsService);
    }

    @Test
    void passwordEncoderBeanExists() {
        assertNotNull(passwordEncoder);
    }

    @Test
    void restTemplateBeanExists() {
        assertNotNull(restTemplate);
    }
}
