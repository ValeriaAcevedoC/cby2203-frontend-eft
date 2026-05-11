package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BackendService backendService;

    @Test
    void shouldAuthenticateWithValidCredentials() throws Exception {
        when(backendService.login(any(AuthRequest.class)))
            .thenReturn(new AuthResponse("valid-token-123"));

        mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"testuser\", \"password\": \"password123\"}"))
            .andExpect(status().isNoContent())
            .andExpect(header().exists("Set-Cookie"));
    }

    @Test
    void shouldReturnCookieOnSuccessfulLogin() throws Exception {
        when(backendService.login(any(AuthRequest.class)))
            .thenReturn(new AuthResponse("test-token"));

        mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"user\", \"password\": \"pass\"}"))
            .andExpect(status().isNoContent())
            .andExpect(header().exists("Set-Cookie"));
    }

    @Test
    void shouldLogoutAndClearCookie() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
            .andExpect(status().isNoContent())
            .andExpect(header().exists("Set-Cookie"));
    }

    @Test
    void shouldReturnSessionInfo() throws Exception {
        mockMvc.perform(get("/api/auth/session")
            .header("Authorization", "Bearer valid-token"))
            .andExpect(status().isNoContent());
    }
}
