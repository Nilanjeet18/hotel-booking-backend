package com.example.demo.service;

import com.example.demo.model.Invoice;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class InvoicePdfService {

    public byte[] generatePdfInvoice(Invoice invoice) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("INVOICE"));
        document.add(new Paragraph("----------------------------"));
        document.add(new Paragraph("Customer: " + invoice.getCustomerName()));
        document.add(new Paragraph("Amount: ₹" + invoice.getTotalAmount()));
        document.add(new Paragraph("GST: ₹" + invoice.getTax()));
        document.add(new Paragraph("Final Amount: ₹" + invoice.getFinalAmount()));

        document.close();

        return baos.toByteArray();
    }
}