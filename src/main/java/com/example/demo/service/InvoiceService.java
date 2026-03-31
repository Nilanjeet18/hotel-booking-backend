package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Booking;
import com.example.demo.model.Invoice;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.InvoiceRepository;

@Service
public class InvoiceService 
{
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice generateInvoice(int bookingId) 
    {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking Not Found"));

        double roomCharges = booking.getTotalAmount();
        double tax = roomCharges * 0.18;
        double total = roomCharges + tax;

        Invoice invoice = new Invoice();
        invoice.setCustomerName(booking.getCustomer().getName());
        invoice.setRoomCharges(roomCharges);
        invoice.setTax(tax);
        invoice.setTotalAmount(total);
        invoice.setBooking(booking);

        return invoiceRepository.save(invoice);
    }
    
    public double calculateGST(double amount) 
    {
        double tax;

        if (amount < 1000) {
            tax = 0;
        } else if (amount <= 7500) {
            tax = amount * 0.12;
        } else {
            tax = amount * 0.18;
        }

        return tax;
    }
    
    public Invoice getInvoiceById(int id) 
    {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice Not Found"));
    }
    
    /*public double calculateTax(double amount) {

        double tax;

        if (amount < 1000) {
            tax = 0;
        } else if (amount <= 7500) {
            tax = amount * 0.12;
        } else {
            tax = amount * 0.18;
        }

        return tax;
    }*/
    
    public double calculateTax(double amount) {
        return amount * 0.18;
    }
}