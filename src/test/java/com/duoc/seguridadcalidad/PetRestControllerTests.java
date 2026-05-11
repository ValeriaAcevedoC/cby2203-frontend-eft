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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BackendService backendService;

    @Test
    void shouldGetAllPets() throws Exception {
        when(backendService.getPets())
            .thenReturn(List.of(
                Map.of("id", 1, "name", "Rex", "species", "Perro"),
                Map.of("id", 2, "name", "Whiskers", "species", "Gato")
            ));

        mockMvc.perform(get("/api/pets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Rex"))
            .andExpect(jsonPath("$[1].species").value("Gato"));
    }

    @Test
    void shouldGetAvailablePets() throws Exception {
        when(backendService.getAvailablePets())
            .thenReturn(List.of(
                Map.of("id", 1, "name", "Available Pet", "available", true)
            ));

        mockMvc.perform(get("/api/pets/available"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldSearchPetsBySpecies() throws Exception {
        when(backendService.searchPets("Perro", null, null, null, null))
            .thenReturn(List.of(
                Map.of("id", 1, "name", "Rex", "species", "Perro")
            ));

        mockMvc.perform(get("/api/pets/search?species=Perro"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldCreatePetWithValidToken() throws Exception {
        when(backendService.createPet(eq("valid-token"), anyMap()))
            .thenReturn(Map.of("id", 1, "name", "NewPet", "species", "Perro"));

        mockMvc.perform(post("/api/pets")
            .header("Authorization", "Bearer valid-token")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"NewPet\", \"species\": \"Perro\"}"))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdatePetWithValidToken() throws Exception {
        when(backendService.updatePet(eq("valid-token"), anyInt(), anyMap()))
            .thenReturn(Map.of("id", 1, "name", "UpdatedPet", "species", "Perro"));

        mockMvc.perform(put("/api/pets/1")
            .header("Authorization", "Bearer valid-token")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"UpdatedPet\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldDeletePetWithValidToken() throws Exception {
        when(backendService.deletePet(eq("valid-token"), anyInt()))
            .thenReturn(Map.of("id", 1, "name", "DeletedPet"));

        mockMvc.perform(delete("/api/pets/1")
            .header("Authorization", "Bearer valid-token"))
            .andExpect(status().isOk());
    }
}
