package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AppointmentTests {

    @Test
    void testAppointmentConstructorAndGetters() {
        LocalDate date = LocalDate.of(2026, 4, 15);
        LocalTime time = LocalTime.of(10, 30);
        Appointment appointment = new Appointment(1L, 5L, date, time, "Checkup", "Dr. Smith");

        assertEquals(1L, appointment.getId());
        assertEquals(5L, appointment.getPatientId());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertEquals("Checkup", appointment.getReason());
        assertEquals("Dr. Smith", appointment.getVeterinarian());
    }

    @Test
    void testAppointmentSetters() {
        Appointment appointment = new Appointment();
        LocalDate date = LocalDate.of(2026, 4, 15);
        LocalTime time = LocalTime.of(14, 0);

        appointment.setId(2L);
        appointment.setPatientId(10L);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setReason("Follow-up");
        appointment.setVeterinarian("Dr. Johnson");

        assertEquals(2L, appointment.getId());
        assertEquals(10L, appointment.getPatientId());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertEquals("Follow-up", appointment.getReason());
        assertEquals("Dr. Johnson", appointment.getVeterinarian());
    }

    @Test
    void testAppointmentDefaultConstructor() {
        Appointment appointment = new Appointment();
        assertNull(appointment.getId());
        assertNull(appointment.getPatientId());
        assertNull(appointment.getDate());
        assertNull(appointment.getTime());
        assertNull(appointment.getReason());
        assertNull(appointment.getVeterinarian());
    }
}
