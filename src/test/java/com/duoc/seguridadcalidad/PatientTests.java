package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PatientTests {

    @Test
    void testPatientConstructorAndGetters() {
        Patient patient = new Patient(1L, "Max", "Perro", "Labrador", 3, "Juan");

        assertEquals(1L, patient.getId());
        assertEquals("Max", patient.getName());
        assertEquals("Perro", patient.getSpecies());
        assertEquals("Labrador", patient.getBreed());
        assertEquals(3, patient.getAge());
        assertEquals("Juan", patient.getOwner());
    }

    @Test
    void testPatientSetters() {
        Patient patient = new Patient();

        patient.setId(2L);
        patient.setName("Luna");
        patient.setSpecies("Gato");
        patient.setBreed("Siamese");
        patient.setAge(5);
        patient.setOwner("Maria");

        assertEquals(2L, patient.getId());
        assertEquals("Luna", patient.getName());
        assertEquals("Gato", patient.getSpecies());
        assertEquals("Siamese", patient.getBreed());
        assertEquals(5, patient.getAge());
        assertEquals("Maria", patient.getOwner());
    }

    @Test
    void testPatientDefaultConstructor() {
        Patient patient = new Patient();

        assertNull(patient.getId());
        assertNull(patient.getName());
        assertNull(patient.getSpecies());
        assertNull(patient.getBreed());
        assertNull(patient.getAge());
        assertNull(patient.getOwner());
    }
}
