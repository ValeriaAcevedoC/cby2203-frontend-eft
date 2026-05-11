package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InvoiceTests {

    @Test
    void testInvoiceConstructorAndGetters() {
        List<InvoiceLineItem> items = new ArrayList<>();
        LocalDate date = LocalDate.of(2026, 4, 15);
        Invoice invoice = new Invoice(
            1L, 
            10L, 
            date, 
            new BigDecimal("19.00"), 
            new BigDecimal("1000.00"),
            new BigDecimal("190.00"),
            new BigDecimal("1190.00"),
            "Payment complete",
            items
        );

        assertEquals(1L, invoice.getId());
        assertEquals(10L, invoice.getAppointmentId());
        assertEquals(date, invoice.getIssueDate());
        assertEquals(new BigDecimal("19.00"), invoice.getVatRate());
        assertEquals(new BigDecimal("1000.00"), invoice.getSubtotal());
        assertEquals(new BigDecimal("190.00"), invoice.getVatAmount());
        assertEquals(new BigDecimal("1190.00"), invoice.getTotal());
        assertEquals("Payment complete", invoice.getNotes());
        assertEquals(items, invoice.getItems());
    }

    @Test
    void testInvoiceSetters() {
        Invoice invoice = new Invoice();
        List<InvoiceLineItem> items = new ArrayList<>();
        LocalDate date = LocalDate.of(2026, 5, 1);

        invoice.setId(2L);
        invoice.setAppointmentId(20L);
        invoice.setIssueDate(date);
        invoice.setVatRate(new BigDecimal("21.00"));
        invoice.setSubtotal(new BigDecimal("5000.00"));
        invoice.setVatAmount(new BigDecimal("1050.00"));
        invoice.setTotal(new BigDecimal("6050.00"));
        invoice.setNotes("Pending payment");
        invoice.setItems(items);

        assertEquals(2L, invoice.getId());
        assertEquals(20L, invoice.getAppointmentId());
        assertEquals(date, invoice.getIssueDate());
        assertEquals(new BigDecimal("21.00"), invoice.getVatRate());
        assertEquals(new BigDecimal("5000.00"), invoice.getSubtotal());
        assertEquals(new BigDecimal("1050.00"), invoice.getVatAmount());
        assertEquals(new BigDecimal("6050.00"), invoice.getTotal());
        assertEquals("Pending payment", invoice.getNotes());
        assertEquals(items, invoice.getItems());
    }

    @Test
    void testInvoiceDefaultConstructor() {
        Invoice invoice = new Invoice();

        assertNull(invoice.getId());
        assertNull(invoice.getAppointmentId());
        assertNull(invoice.getIssueDate());
        assertNull(invoice.getVatRate());
        assertNull(invoice.getSubtotal());
        assertNull(invoice.getVatAmount());
        assertNull(invoice.getTotal());
        assertNull(invoice.getNotes());
        assertEquals(0, invoice.getItems().size());
    }
}
