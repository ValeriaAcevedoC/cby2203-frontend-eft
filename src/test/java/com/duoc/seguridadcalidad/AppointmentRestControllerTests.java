package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BackendService backendService;

    @Test
    void shouldGetAppointmentsWithValidToken() throws Exception {
        when(backendService.getAppointments("valid-token"))
            .thenReturn(List.of(
                Map.of("id", 1, "patientId", 1, "reason", "Checkup"),
                Map.of("id", 2, "patientId", 2, "reason", "Follow-up")
            ));

        mockMvc.perform(get("/api/appointments")
            .header("Authorization", "Bearer valid-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].reason").value("Follow-up"));
    }

    @Test
    void shouldReturnUnauthorizedWithoutToken() throws Exception {
        mockMvc.perform(get("/api/appointments"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreateAppointmentWithValidToken() throws Exception {
        Map<String, Object> appointmentData = Map.of(
            "patientId", 1,
            "reason", "Checkup"
        );

        when(backendService.createAppointment(eq("valid-token"), anyMap()))
            .thenReturn(Map.of("id", 1, "patientId", 1, "reason", "Checkup"));

        mockMvc.perform(post("/api/appointments")
            .header("Authorization", "Bearer valid-token")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"patientId\": 1, \"reason\": \"Checkup\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorizedOnCreateWithoutToken() throws Exception {
        mockMvc.perform(post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"patientId\": 1}"))
            .andExpect(status().isUnauthorized());
    }
}
