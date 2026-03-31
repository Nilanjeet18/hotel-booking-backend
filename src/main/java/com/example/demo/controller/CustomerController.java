package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Booking;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

import org.springframework.data.domain.Page;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController 
{
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable int id,
                                   @Valid @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }

    @GetMapping("/search/name")
    public List<Customer> searchByName(@RequestParam String name) {
        return customerService.searchByName(name);
    }

    @GetMapping("/search/phone")
    public List<Customer> searchByPhone(@RequestParam String phone) {
        return customerService.searchByPhone(phone);
    }
    
    @GetMapping("/{id}/history")
    public List<Booking> getCustomerHistory(@PathVariable int id) {
        return customerService.getCustomerHistory(id);
    }

    @GetMapping("/paged")
    public Page<Customer> getCustomersWithPagination(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "customerId") String sortBy) {

        return customerService.getCustomersWithPagination(page, size, sortBy);
    }

}
