package com.example.demo.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.Booking;
import com.example.demo.model.Customer;
import com.example.demo.model.Room;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoomRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class InvoiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void testGenerateInvoice() throws Exception {

        // 🟢 1. Create Customer
        Customer customer = new Customer();
        customer.setName("Test User");
        customer.setEmail("testuser@gmail.com");
        customer.setPhone("9876543210");

        Customer savedCustomer = customerRepository.save(customer);

        // 🟢 2. Create Room
        Room room = new Room();
        room.setPrice(5000);

        Room savedRoom = roomRepository.save(room);

        // 🟢 3. Create Booking (ALL required fields)
        Booking booking = new Booking();
        booking.setCustomer(savedCustomer);
        booking.setRoom(savedRoom);
        booking.setStatus("CONFIRMED");
        booking.setTotalAmount(5000.0);
        booking.setCheckIn(LocalDate.now());
        booking.setCheckOut(LocalDate.now().plusDays(2));

        Booking savedBooking = bookingRepository.save(booking);

        // 🟢 4. Call API using REAL ID
        mockMvc.perform(post("/api/invoice/generate/" + savedBooking.getBookingId()))
                .andExpect(status().isOk());
    }
}