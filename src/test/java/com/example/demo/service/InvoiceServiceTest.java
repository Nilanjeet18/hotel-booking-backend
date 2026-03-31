package com.example.demo.service;

import com.example.demo.repository.BookingRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    void testServiceNotNull() {
        assertNotNull(invoiceService);
    }
    
    @Test
    void testCalculateTax() {
        double result = invoiceService.calculateTax(1000);
        assertEquals(180, result);
    }
}