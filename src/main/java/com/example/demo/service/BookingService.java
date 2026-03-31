package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.BookingDTO;
import com.example.demo.model.Booking;

public interface BookingService {

    Booking createBooking(int roomId, int customerId, LocalDate checkIn, LocalDate checkOut);

    String cancelBooking(int bookingId);

    BookingDTO createBooking1(BookingDTO bookingDTO);

    List<Booking> getAllBookings();

    // ✅ NEW — availability check
    boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut);

    // ✅ NEW — update booking dates
    BookingDTO updateBooking(int bookingId, BookingDTO bookingDTO);

    // ✅ NEW — get single booking by id
    Booking getBookingById(int bookingId);

    Double getDailyRevenue(LocalDate date);

    Double getMonthlyOccupancy(int month, int year);

    List<Booking> getCustomerBookings(int id);
}
