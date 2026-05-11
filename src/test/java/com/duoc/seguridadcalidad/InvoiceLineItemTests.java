package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InvoiceLineItemTests {

    @Test
    void testInvoiceLineItemConstructorAndGetters() {
        InvoiceLineItem item = new InvoiceLineItem(
            1L,
            InvoiceLineItemType.SERVICE,
            "Veterinary Checkup",
            2,
            new BigDecimal("100.00"),
            new BigDecimal("200.00")
        );

        assertEquals(1L, item.getId());
        assertEquals(InvoiceLineItemType.SERVICE, item.getType());
        assertEquals("Veterinary Checkup", item.getDescription());
        assertEquals(2, item.getQuantity());
        assertEquals(new BigDecimal("100.00"), item.getUnitPrice());
        assertEquals(new BigDecimal("200.00"), item.getLineTotal());
    }

    @Test
    void testInvoiceLineItemSetters() {
        InvoiceLineItem item = new InvoiceLineItem();

        item.setId(2L);
        item.setType(InvoiceLineItemType.MEDICATION);
        item.setDescription("Antibiotics");
        item.setQuantity(5);
        item.setUnitPrice(new BigDecimal("50.00"));
        item.setLineTotal(new BigDecimal("250.00"));

        assertEquals(2L, item.getId());
        assertEquals(InvoiceLineItemType.MEDICATION, item.getType());
        assertEquals("Antibiotics", item.getDescription());
        assertEquals(5, item.getQuantity());
        assertEquals(new BigDecimal("50.00"), item.getUnitPrice());
        assertEquals(new BigDecimal("250.00"), item.getLineTotal());
    }

    @Test
    void testInvoiceLineItemDefaultConstructor() {
        InvoiceLineItem item = new InvoiceLineItem();

        assertNull(item.getId());
        assertNull(item.getType());
        assertNull(item.getDescription());
        assertNull(item.getQuantity());
        assertNull(item.getUnitPrice());
        assertNull(item.getLineTotal());
    }
}
