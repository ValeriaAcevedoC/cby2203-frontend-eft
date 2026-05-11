package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BackendServiceTests {

    @Mock
    private RestTemplate restTemplate;

    private BackendService backendService;

    private static final String BASE_URL = "http://localhost:8080";
    private static final String TOKEN = "test-jwt-token";
    private static final String LOGIN_PATH = "/login";
    private static final String PATIENTS_PATH = "/patients";
    private static final String APPOINTMENTS_PATH = "/appointments";
    private static final String PETS_PATH = "/pets";
    private static final String PETS_SEARCH_PATH = "/pets/search";
    private static final String PETS_UPDATE_PATH = "/pets/8";
    private static final String INVOICES_PATH = "/invoices";

    private static final String TEST_TOKEN = "my-token";
    private static final String UNAUTHORIZED_MESSAGE = "Unauthorized";
    private static final String BAD_REQUEST_MESSAGE = "Bad Request";
    private static final String TEST_DATE = "2026-05-01";
    private static final String UPDATED_PET_NAME = "Buddy Updated";
    private static final String NOT_FOUND_MESSAGE = "Not Found";
    private static final String TOTAL_MESSAGE = "total";

    @BeforeEach
    void setUp() {
        backendService = new BackendService(restTemplate, BASE_URL);
    }

    // -------------------------------------------------------------------------
    // login
    // -------------------------------------------------------------------------

    @Test
    void loginShouldReturnTokenWhenBackendRespondsWithRawToken() {
        AuthRequest request = new AuthRequest();
        request.setUsername("user");
        request.setPassword("pass");

        when(restTemplate.postForEntity(BASE_URL + LOGIN_PATH, request, String.class))
                .thenReturn(ResponseEntity.ok(TEST_TOKEN));

        AuthResponse response = backendService.login(request);

        assertEquals(TEST_TOKEN, response.getToken());
    }

    @Test
    void loginShouldStripBearerPrefixFromToken() {
        AuthRequest request = new AuthRequest();
        request.setUsername("user");
        request.setPassword("pass");

        when(restTemplate.postForEntity(BASE_URL + LOGIN_PATH, request, String.class))
                .thenReturn(ResponseEntity.ok("Bearer my-token"));

        AuthResponse response = backendService.login(request);

        assertEquals(TEST_TOKEN, response.getToken());
    }

    @Test
    void loginShouldReturnNullTokenWhenBodyIsNull() {
        AuthRequest request = new AuthRequest();

        when(restTemplate.postForEntity(BASE_URL + LOGIN_PATH, request, String.class))
                .thenReturn(ResponseEntity.ok(null));

        AuthResponse response = backendService.login(request);

        assertNull(response.getToken());
    }

    @Test
    void loginShouldPropagateHttpStatusCodeException() {
        AuthRequest request = new AuthRequest();

        when(restTemplate.postForEntity(BASE_URL + LOGIN_PATH, request, String.class))
                .thenThrow(HttpClientErrorException.create(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.login(request));
    }

    // -------------------------------------------------------------------------
    // getPatients
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void getPatientsShouldReturnListOfPatients() {
        Map<String, Object> patient = Map.of("id", 1, "name", "John");

        when(restTemplate.exchange(eq(BASE_URL + PATIENTS_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(new Map[]{patient}));

        List<Map<String, Object>> result = backendService.getPatients(TOKEN);

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).get("name"));
    }

    @Test
    void getPatientsShouldReturnEmptyListWhenBodyIsNull() {
        when(restTemplate.exchange(eq(BASE_URL + PATIENTS_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(null));

        List<Map<String, Object>> result = backendService.getPatients(TOKEN);

        assertTrue(result.isEmpty());
    }

    @Test
    void getPatientsShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(eq(BASE_URL + PATIENTS_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.FORBIDDEN, "Forbidden", null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.getPatients(TOKEN));
    }

    // -------------------------------------------------------------------------
    // createPatient
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void createPatientShouldReturnCreatedPatient() {
        Map<String, Object> patient = Map.of("name", "Jane");
        Map<String, Object> created = Map.of("id", 2, "name", "Jane");

        when(restTemplate.postForObject(eq(BASE_URL + PATIENTS_PATH), any(), eq(Map.class)))
                .thenReturn(created);

        Map<String, Object> result = backendService.createPatient(TOKEN, patient);

        assertEquals(2, result.get("id"));
    }

    @Test
    void createPatientShouldPropagateHttpStatusCodeException() {
        when(restTemplate.postForObject(eq(BASE_URL + PATIENTS_PATH), any(), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.createPatient(TOKEN, Map.of()));
    }

    // -------------------------------------------------------------------------
    // getAppointments
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void getAppointmentsShouldReturnListOfAppointments() {
        Map<String, Object> appt = Map.of("id", 10, "date", TEST_DATE);

        when(restTemplate.exchange(eq(BASE_URL + APPOINTMENTS_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(new Map[]{appt}));

        List<Map<String, Object>> result = backendService.getAppointments(TOKEN);

        assertEquals(1, result.size());
        assertEquals(10, result.get(0).get("id"));
    }

    @Test
    void getAppointmentsShouldReturnEmptyListWhenBodyIsNull() {
        when(restTemplate.exchange(eq(BASE_URL + APPOINTMENTS_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(null));

        assertTrue(backendService.getAppointments(TOKEN).isEmpty());
    }

    @Test
    void getAppointmentsShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(eq(BASE_URL + APPOINTMENTS_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.getAppointments(TOKEN));
    }

    // -------------------------------------------------------------------------
    // createAppointment
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void createAppointmentShouldReturnCreatedAppointment() {
        Map<String, Object> appt = Map.of("date", TEST_DATE);
        Map<String, Object> created = Map.of("id", 11, "date", TEST_DATE);

        when(restTemplate.postForObject(eq(BASE_URL + APPOINTMENTS_PATH), any(), eq(Map.class)))
                .thenReturn(created);

        Map<String, Object> result = backendService.createAppointment(TOKEN, appt);

        assertEquals(11, result.get("id"));
    }

    @Test
    void createAppointmentShouldPropagateHttpStatusCodeException() {
        when(restTemplate.postForObject(eq(BASE_URL + APPOINTMENTS_PATH), any(), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.createAppointment(TOKEN, Map.of()));
    }

    // -------------------------------------------------------------------------
    // getPets
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void getPetsShouldReturnListOfPets() {
        Map<String, Object> pet = Map.of("id", 5, "name", "Rex");

        when(restTemplate.exchange(eq(BASE_URL + PETS_PATH), eq(HttpMethod.GET), isNull(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(new Map[]{pet}));

        List<Map<String, Object>> result = backendService.getPets();

        assertEquals(1, result.size());
        assertEquals("Rex", result.get(0).get("name"));
    }

    @Test
    void getPetsShouldReturnEmptyListWhenBodyIsNull() {
        when(restTemplate.exchange(eq(BASE_URL + PETS_PATH), eq(HttpMethod.GET), isNull(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(null));

        assertTrue(backendService.getPets().isEmpty());
    }

    @Test
    void getPetsShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(eq(BASE_URL + PETS_PATH), eq(HttpMethod.GET), isNull(), eq(Map[].class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error", null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.getPets());
    }

    // -------------------------------------------------------------------------
    // getAvailablePets
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void getAvailablePetsShouldReturnListOfAvailablePets() {
        Map<String, Object> pet = Map.of("id", 6, "status", "available");

        when(restTemplate.exchange(eq(BASE_URL + "/pets/available"), eq(HttpMethod.GET), isNull(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(new Map[]{pet}));

        List<Map<String, Object>> result = backendService.getAvailablePets();

        assertEquals(1, result.size());
        assertEquals("available", result.get(0).get("status"));
    }

    @Test
    void getAvailablePetsShouldReturnEmptyListWhenBodyIsNull() {
        when(restTemplate.exchange(eq(BASE_URL + "/pets/available"), eq(HttpMethod.GET), isNull(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(null));

        assertTrue(backendService.getAvailablePets().isEmpty());
    }

    // -------------------------------------------------------------------------
    // searchPets
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void searchPetsShouldBuildUriWithProvidedParameters() {
        Map<String, Object> pet = Map.of("id", 7, "species", "dog");

        when(restTemplate.exchange(contains(PETS_SEARCH_PATH), eq(HttpMethod.GET), isNull(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(new Map[]{pet}));

        List<Map<String, Object>> result = backendService.searchPets("dog", null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("dog", result.get(0).get("species"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void searchPetsShouldReturnEmptyListWhenBodyIsNull() {
        when(restTemplate.exchange(contains(PETS_SEARCH_PATH), eq(HttpMethod.GET), isNull(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(null));

        assertTrue(backendService.searchPets(null, null, null, null, null).isEmpty());
    }

    @Test
    void searchPetsShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(contains(PETS_SEARCH_PATH), eq(HttpMethod.GET), isNull(), eq(Map[].class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.searchPets("dog", null, null, null, null));
    }

    // -------------------------------------------------------------------------
    // createPet
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void createPetShouldReturnCreatedPet() {
        Map<String, Object> pet = Map.of("name", "Buddy");
        Map<String, Object> created = Map.of("id", 8, "name", "Buddy");

        when(restTemplate.postForObject(eq(BASE_URL + PETS_PATH), any(), eq(Map.class)))
                .thenReturn(created);

        Map<String, Object> result = backendService.createPet(TOKEN, pet);

        assertEquals(8, result.get("id"));
    }

    @Test
    void createPetShouldPropagateHttpStatusCodeException() {
        when(restTemplate.postForObject(eq(BASE_URL + PETS_PATH), any(), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.createPet(TOKEN, Map.of()));
    }

    // -------------------------------------------------------------------------
    // updatePet
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void updatePetShouldReturnUpdatedPet() {
        Map<String, Object> pet = Map.of("name", UPDATED_PET_NAME);
        Map<String, Object> updated = Map.of("id", 8, "name", UPDATED_PET_NAME);

        when(restTemplate.exchange(eq(BASE_URL + PETS_UPDATE_PATH), eq(HttpMethod.PUT), any(), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(updated));

        Map<String, Object> result = backendService.updatePet(TOKEN, 8, pet);

        assertEquals(UPDATED_PET_NAME, result.get("name"));
    }

    @Test
    void updatePetShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(eq(BASE_URL + PETS_UPDATE_PATH), eq(HttpMethod.PUT), any(), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.updatePet(TOKEN, 8, Map.of()));
    }

    // -------------------------------------------------------------------------
    // deletePet
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void deletePetShouldReturnResponseBody() {
        Map<String, Object> deleted = Map.of("message", "deleted");

        when(restTemplate.exchange(eq(BASE_URL + PETS_UPDATE_PATH), eq(HttpMethod.DELETE), any(), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(deleted));

        Map<String, Object> result = backendService.deletePet(TOKEN, 8);

        assertEquals("deleted", result.get("message"));
    }

    @Test
    void deletePetShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(eq(BASE_URL + PETS_UPDATE_PATH), eq(HttpMethod.DELETE), any(), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.deletePet(TOKEN, 8));
    }

    // -------------------------------------------------------------------------
    // getInvoices
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void getInvoicesShouldReturnListOfInvoices() {
        Map<String, Object> invoice = Map.of("id", 100, TOTAL_MESSAGE, 50.0);

        when(restTemplate.exchange(eq(BASE_URL + INVOICES_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(new Map[]{invoice}));

        List<Map<String, Object>> result = backendService.getInvoices(TOKEN);

        assertEquals(1, result.size());
        assertEquals(100, result.get(0).get("id"));
    }

    @Test
    void getInvoicesShouldReturnEmptyListWhenBodyIsNull() {
        when(restTemplate.exchange(eq(BASE_URL + INVOICES_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(ResponseEntity.ok(null));

        assertTrue(backendService.getInvoices(TOKEN).isEmpty());
    }

    @Test
    void getInvoicesShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(eq(BASE_URL + INVOICES_PATH), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.getInvoices(TOKEN));
    }

    // -------------------------------------------------------------------------
    // getInvoiceById
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void getInvoiceByIdShouldReturnInvoice() {
        Map<String, Object> invoice = Map.of("id", 100L, TOTAL_MESSAGE, 50.0);

        when(restTemplate.exchange(eq(BASE_URL + "/invoices/100"), eq(HttpMethod.GET), any(), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(invoice));

        Map<String, Object> result = backendService.getInvoiceById(TOKEN, 100L);

        assertEquals(100L, result.get("id"));
    }

    @Test
    void getInvoiceByIdShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(eq(BASE_URL + "/invoices/100"), eq(HttpMethod.GET), any(), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.getInvoiceById(TOKEN, 100L));
    }

    // -------------------------------------------------------------------------
    // getInvoiceByAppointmentId
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void getInvoiceByAppointmentIdShouldReturnInvoice() {
        Map<String, Object> invoice = Map.of("id", 100L, "appointmentId", 10L);

        when(restTemplate.exchange(eq(BASE_URL + "/invoices/appointment/10"), eq(HttpMethod.GET), any(), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(invoice));

        Map<String, Object> result = backendService.getInvoiceByAppointmentId(TOKEN, 10L);

        assertEquals(10L, result.get("appointmentId"));
    }

    @Test
    void getInvoiceByAppointmentIdShouldPropagateHttpStatusCodeException() {
        when(restTemplate.exchange(eq(BASE_URL + "/invoices/appointment/10"), eq(HttpMethod.GET), any(), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.getInvoiceByAppointmentId(TOKEN, 10L));
    }

    // -------------------------------------------------------------------------
    // createInvoice
    // -------------------------------------------------------------------------

    @Test
    @SuppressWarnings("unchecked")
    void createInvoiceShouldReturnCreatedInvoice() {
        Map<String, Object> request = Map.of(TOTAL_MESSAGE, 75.0);
        Map<String, Object> created = Map.of("id", 101L, TOTAL_MESSAGE, 75.0);

        when(restTemplate.postForObject(eq(BASE_URL + "/invoices/appointments/10"), any(), eq(Map.class)))
                .thenReturn(created);

        Map<String, Object> result = backendService.createInvoice(TOKEN, 10L, request);

        assertEquals(101L, result.get("id"));
    }

    @Test
    void createInvoiceShouldPropagateHttpStatusCodeException() {
        when(restTemplate.postForObject(eq(BASE_URL + "/invoices/appointments/10"), any(), eq(Map.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE, null, null, null));

        assertThrows(HttpClientErrorException.class, () -> backendService.createInvoice(TOKEN, 10L, Map.of()));
    }
}
