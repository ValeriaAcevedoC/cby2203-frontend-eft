package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InvoiceCreateRequestTests {

    @Test
    void testInvoiceCreateRequestSettersAndGetters() {
        InvoiceCreateRequest request = new InvoiceCreateRequest();
        List<InvoiceLineItem> items = new ArrayList<>();
        LocalDate date = LocalDate.of(2026, 4, 15);

        request.setIssueDate(date);
        request.setVatRate(new BigDecimal("19.00"));
        request.setNotes("Invoice notes");
        request.setItems(items);

        assertEquals(date, request.getIssueDate());
        assertEquals(new BigDecimal("19.00"), request.getVatRate());
        assertEquals("Invoice notes", request.getNotes());
        assertEquals(items, request.getItems());
    }

    @Test
    void testInvoiceCreateRequestDefaultConstructor() {
        InvoiceCreateRequest request = new InvoiceCreateRequest();

        assertNull(request.getIssueDate());
        assertNull(request.getVatRate());
        assertNull(request.getNotes());
        // Items is initialized to an empty list by default, not null
    }

    @Test
    void testInvoiceCreateRequestWithMultipleItems() {
        InvoiceCreateRequest request = new InvoiceCreateRequest();
        List<InvoiceLineItem> items = new ArrayList<>();
        InvoiceLineItem item1 = new InvoiceLineItem();
        item1.setDescription("Item 1");
        InvoiceLineItem item2 = new InvoiceLineItem();
        item2.setDescription("Item 2");
        items.add(item1);
        items.add(item2);

        request.setItems(items);

        assertEquals(2, request.getItems().size());
    }
}
