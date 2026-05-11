package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthResponseTests {

    @Test
    void testAuthResponseConstructor() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        AuthResponse response = new AuthResponse(token);

        assertEquals(token, response.getToken());
    }

    @Test
    void testAuthResponseWithDifferentTokens() {
        String token1 = "token123";
        String token2 = "token456";

        AuthResponse response1 = new AuthResponse(token1);
        AuthResponse response2 = new AuthResponse(token2);

        assertEquals(token1, response1.getToken());
        assertEquals(token2, response2.getToken());
    }

    @Test
    void testAuthResponseWithEmptyToken() {
        AuthResponse response = new AuthResponse("");
        assertEquals("", response.getToken());
    }
}
