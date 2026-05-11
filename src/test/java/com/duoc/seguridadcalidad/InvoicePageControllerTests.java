package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoicePageControllerTests {

    private final InvoicePageController invoicePageController = new InvoicePageController();

    @Test
    void listInvoicesShouldReturnInvoicesView() {
        assertEquals("invoices", invoicePageController.listInvoices());
    }

    @Test
    void showCreateFormShouldReturnNewInvoiceView() {
        assertEquals("new_invoice", invoicePageController.showCreateForm());
    }

    @Test
    void showInvoiceDetailShouldReturnInvoiceDetailView() {
        assertEquals("invoice_detail", invoicePageController.showInvoiceDetail(1L));
    }
}
