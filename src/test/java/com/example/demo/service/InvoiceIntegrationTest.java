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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class InvoiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoomRepository roomRepository;

    @Test
    void testGenerateInvoice() throws Exception {

        // 1️⃣ Customer create
        Customer customer = new Customer();
        customer.setName("Test User");
        customer.setEmail("swork2804@gmail.com");   // 🔥 IMPORTANT
        customer.setPhone("9876543210");       // जर required असेल तर
        Customer savedCustomer = customerRepository.save(customer);

        // 2️⃣ Room create
        Room room = new Room();
        room.setPrice(5000);
        Room savedRoom = roomRepository.save(room);

        // 3️⃣ Booking create (ALL required fields set कर)
        Booking booking = new Booking();
        booking.setCustomer(savedCustomer);
        booking.setRoom(savedRoom);
        booking.setStatus("CONFIRMED");
        booking.setTotalAmount(5000.0);
        booking.setCheckIn(LocalDate.now());
        booking.setCheckOut(LocalDate.now().plusDays(2));

        Booking savedBooking = bookingRepository.save(booking);

        // 4️⃣ Call API
        mockMvc.perform(post("/api/invoice/generate/" + savedBooking.getBookingId()))
                .andExpect(status().isOk());
    }
}