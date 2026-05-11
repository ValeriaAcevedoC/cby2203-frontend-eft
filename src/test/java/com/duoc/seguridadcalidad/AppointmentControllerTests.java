package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentControllerTests {

    private final AppointmentController appointmentController = new AppointmentController();

    @Test
    void listAppointmentsShouldReturnAppointmentsView() {
        assertEquals("appointments", appointmentController.listAppointments());
    }

    @Test
    void showCreateFormShouldReturnNewAppointmentView() {
        assertEquals("new_appointment", appointmentController.showCreateForm());
    }

    @Test
    void saveAppointmentShouldRedirectToAppointments() {
        assertEquals("redirect:/appointments", appointmentController.saveAppointment());
    }
}
