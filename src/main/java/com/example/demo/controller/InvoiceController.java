package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Invoice;
import com.example.demo.service.InvoicePdfService;
import com.example.demo.service.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoicePdfService invoicePdfService;

    @PostMapping("/generate/{bookingId}")
    public ResponseEntity<Invoice> generate(@PathVariable int bookingId) {
        return ResponseEntity.ok(invoiceService.generateInvoice(bookingId));
    }

    @GetMapping("/pdf/{id}")   // URL थोडा clean केला
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable int id) throws Exception 
    {
        Invoice invoice = invoiceService.getInvoiceById(id);

        byte[] pdfBytes = invoicePdfService.generatePdfInvoice(invoice);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}