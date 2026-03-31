package com.example.demo.dto;

import java.time.LocalDate;

public class BookingResponseDTO {

    private int bookingId;
    private int customerId;
    private int roomId;
    private LocalDate checkInDate;
    private double amount;
    private String status;

    public BookingResponseDTO() {}

    public BookingResponseDTO(int bookingId, int customerId, int roomId,
                              LocalDate checkInDate, double amount, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.amount = amount;
        this.status = status;
    }

    // getters
    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public int getRoomId() { return roomId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
}