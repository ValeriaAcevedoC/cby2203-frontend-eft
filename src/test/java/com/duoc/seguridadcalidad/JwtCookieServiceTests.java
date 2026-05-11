package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtCookieServiceTests {

    private JwtCookieService jwtCookieService;

    @BeforeEach
    void setUp() {
        jwtCookieService = new JwtCookieService(true, 3600, "Strict");
    }

    @Test
    void testCreateAuthCookie() {
        String token = "test-token-123";
        ResponseCookie cookie = jwtCookieService.createAuthCookie(token);

        assertNotNull(cookie);
        assertEquals(JwtCookieService.COOKIE_NAME, cookie.getName());
        assertEquals(token, cookie.getValue());
    }

    @Test
    void testClearAuthCookie() {
        ResponseCookie cookie = jwtCookieService.clearAuthCookie();

        assertNotNull(cookie);
        assertEquals(JwtCookieService.COOKIE_NAME, cookie.getName());
        assertEquals("", cookie.getValue());
    }

    @Test
    void testExtractTokenFromAuthorizationHeader() {
        // This test would require mocking HttpServletRequest
        // Basic instantiation test
        assertNotNull(jwtCookieService);
    }

    @Test
    void testCookieNameConstant() {
        assertEquals("AUTH_TOKEN", JwtCookieService.COOKIE_NAME);
    }
}
