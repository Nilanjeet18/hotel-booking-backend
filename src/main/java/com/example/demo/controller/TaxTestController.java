package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.InvoiceService;

@RestController
@RequestMapping("/api/test")
public class TaxTestController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/gst")
    public String testGST(@RequestParam double amount)
    {
        double tax = invoiceService.calculateTax(amount);
        double total = amount + tax;

        return "Amount: " + amount + 
               " | Tax: " + tax + 
               " | Total: " + total;
    }
}