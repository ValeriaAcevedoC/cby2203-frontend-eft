package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthRequestTests {

    @Test
    void testAuthRequestSettersAndGetters() {
        AuthRequest authRequest = new AuthRequest();
        
        authRequest.setUsername("testuser");
        authRequest.setPassword("password123");

        assertEquals("testuser", authRequest.getUsername());
        assertEquals("password123", authRequest.getPassword());
    }

    @Test
    void testAuthRequestInitiallyNull() {
        AuthRequest authRequest = new AuthRequest();
        
        assertNull(authRequest.getUsername());
        assertNull(authRequest.getPassword());
    }

    @Test
    void testAuthRequestMultipleUsers() {
        AuthRequest req1 = new AuthRequest();
        req1.setUsername("user1");
        req1.setPassword("pass1");

        AuthRequest req2 = new AuthRequest();
        req2.setUsername("user2");
        req2.setPassword("pass2");

        assertEquals("user1", req1.getUsername());
        assertEquals("user2", req2.getUsername());
    }
}
