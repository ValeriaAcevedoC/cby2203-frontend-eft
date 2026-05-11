package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientControllerTests {

    private final PatientController patientController = new PatientController();

    @Test
    void listPatientsShouldReturnPatientsView() {
        assertEquals("patients", patientController.listPatients());
    }

    @Test
    void showCreateFormShouldReturnNewPatientView() {
        assertEquals("new_patient", patientController.showCreateForm());
    }

    @Test
    void savePatientShouldRedirectToPatients() {
        assertEquals("redirect:/patients", patientController.savePatient());
    }
}
