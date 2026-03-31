package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.model.Booking;
import com.example.demo.model.Customer;
import com.example.demo.model.Room;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoomRepository;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ────────────────────────────────────────────────────────────────
    // ✅ IS ROOM AVAILABLE — check karo dates overlap hot aahet ka
    // ────────────────────────────────────────────────────────────────
    @Override
    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
        // Room exist karto ka check
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // BOOKED aahe tar check karo dates overlap hotat ka
        List<Booking> overlapping = bookingRepository
                .findOverlappingBookings(roomId, checkIn, checkOut);

        return overlapping.isEmpty(); // empty mhanaje available
    }

    // ────────────────────────────────────────────────────────────────
    // ✅ GET BOOKING BY ID
    // ────────────────────────────────────────────────────────────────
    @Override
    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
    }

    // ────────────────────────────────────────────────────────────────
    // ✅ UPDATE BOOKING — checkIn/checkOut dates update karo
    // ────────────────────────────────────────────────────────────────
    @Override
    public BookingDTO updateBooking(int bookingId, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        // Dates validate karo
        LocalDate newCheckIn  = bookingDTO.getCheckInDate();
        LocalDate newCheckOut = bookingDTO.getCheckOutDate();

        long days = ChronoUnit.DAYS.between(newCheckIn, newCheckOut);
        if (days <= 0) {
            throw new RuntimeException("Invalid dates: check-out must be after check-in");
        }

        // Amount recalculate karo
        double totalAmount = days * booking.getRoom().getPrice();

        booking.setCheckIn(newCheckIn);
        booking.setCheckOut(newCheckOut);
        booking.setTotalAmount(totalAmount);

        Booking updated = bookingRepository.save(booking);
        return modelMapper.map(updated, BookingDTO.class);
    }

    // ────────────────────────────────────────────────────────────────
    // Existing methods — unchanged
    // ────────────────────────────────────────────────────────────────

    @Override
    public Booking createBooking(int roomId, int customerId,
                                 LocalDate checkIn, LocalDate checkOut) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!room.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("Room not available");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (days <= 0) throw new RuntimeException("Invalid date");

        double totalAmount = days * room.getPrice();

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setCustomer(customer);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setTotalAmount(totalAmount);
        booking.setStatus("CONFIRMED");

        room.setStatus("BOOKED");
        roomRepository.save(room);

        return bookingRepository.save(booking);
    }

    @Override
    public String cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus("CANCELLED");

        Room room = booking.getRoom();
        room.setStatus("AVAILABLE");

        roomRepository.save(room);
        bookingRepository.save(booking);

        return "Booking Cancelled Successfully";
    }

    @Override
    public BookingDTO createBooking1(BookingDTO bookingDTO) {
        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!room.getStatus().equalsIgnoreCase("AVAILABLE")) {
            throw new RuntimeException("Room not available");
        }

        Customer customer = customerRepository.findById(bookingDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        long days = ChronoUnit.DAYS.between(
                bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());

        if (days <= 0) throw new RuntimeException("Invalid dates");

        double totalAmount = days * room.getPrice();

        Booking booking = new Booking();
        booking.setCheckIn(bookingDTO.getCheckInDate());
        booking.setCheckOut(bookingDTO.getCheckOutDate());
        booking.setRoom(room);
        booking.setCustomer(customer);
        booking.setStatus("CONFIRMED");
        booking.setTotalAmount(totalAmount);

        room.setStatus("BOOKED");
        roomRepository.save(room);

        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Double getDailyRevenue(LocalDate date) {
        Double revenue = bookingRepository.getDailyRevenue(date);
        return revenue != null ? revenue : 0.0;
    }

    @Override
    public Double getMonthlyOccupancy(int month, int year) {
        Long bookedRooms = bookingRepository.countMonthlyBookings(month, year);
        if (bookedRooms == null) bookedRooms = 0L;
        double totalRooms = 50.0;
        return (bookedRooms / totalRooms) * 100;
    }

    @Override
    public List<Booking> getCustomerBookings(int id) {
        return bookingRepository.findByCustomerCustomerId(id);
    }
}
