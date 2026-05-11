package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppointmentRepositoryTests {

    private AppointmentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new AppointmentRepository();
    }

    @Test
    void findAllShouldReturnEmptyListInitially() {
        List<Appointment> appointments = repository.findAll();
        assertEquals(0, appointments.size());
    }

    @Test
    void saveShouldAssignIdAndPersistAppointment() {
        Appointment appointment = new Appointment(
            null, 
            1L, 
            LocalDate.of(2026, 5, 1), 
            LocalTime.of(10, 0), 
            "Checkup", 
            "Dr. Smith"
        );

        Appointment saved = repository.save(appointment);

        assertNotNull(saved.getId());
        assertEquals(1L, saved.getId());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void saveShouldIncrementIdForMultipleAppointments() {
        Appointment app1 = new Appointment(null, 1L, LocalDate.now(), LocalTime.now(), "Checkup", "Dr. A");
        Appointment app2 = new Appointment(null, 2L, LocalDate.now(), LocalTime.now(), "Checkup", "Dr. B");

        repository.save(app1);
        repository.save(app2);

        List<Appointment> all = repository.findAll();
        assertEquals(2, all.size());
        assertEquals(1L, all.get(0).getId());
        assertEquals(2L, all.get(1).getId());
    }

    @Test
    void saveShouldPreserveExistingId() {
        Appointment appointment = new Appointment(99L, 1L, LocalDate.now(), LocalTime.now(), "Checkup", "Dr. Smith");

        Appointment saved = repository.save(appointment);

        assertEquals(99L, saved.getId());
    }
}
