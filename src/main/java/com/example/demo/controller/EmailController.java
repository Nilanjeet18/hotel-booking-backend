package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController 
{
    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public String testMail() 
    {
        emailService.sendEmail("ruturajgursale2757@gmail.com");
        return "Email Sent Successfully!";
    }
}
