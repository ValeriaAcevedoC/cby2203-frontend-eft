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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BackendService backendService;

    @Test
    void shouldGetPatientsWithValidToken() throws Exception {
        when(backendService.getPatients("valid-token"))
            .thenReturn(List.of(
                Map.of("id", 1, "name", "Max", "species", "Perro"),
                Map.of("id", 2, "name", "Luna", "species", "Gato")
            ));

        mockMvc.perform(get("/api/patients")
            .header("Authorization", "Bearer valid-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Max"))
            .andExpect(jsonPath("$[1].species").value("Gato"));
    }

    @Test
    void shouldReturnUnauthorizedWithoutToken() throws Exception {
        mockMvc.perform(get("/api/patients"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreatePatientWithValidToken() throws Exception {
        Map<String, Object> patientData = Map.of(
            "name", "Buddy",
            "species", "Perro"
        );

        when(backendService.createPatient(eq("valid-token"), anyMap()))
            .thenReturn(Map.of("id", 1, "name", "Buddy", "species", "Perro"));

        mockMvc.perform(post("/api/patients")
            .header("Authorization", "Bearer valid-token")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Buddy\", \"species\": \"Perro\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorizedOnCreateWithoutToken() throws Exception {
        mockMvc.perform(post("/api/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Buddy\"}"))
            .andExpect(status().isUnauthorized());
    }
}
