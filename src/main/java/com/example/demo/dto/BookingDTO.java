package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;

public class BookingDTO {

	//private Integer bookingId;

    @NotNull(message = "Room ID is required")
    private int roomId;

    @NotNull(message = "Customer ID is required")
    private int customerId;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be in future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @FutureOrPresent(message = "Check-out date must be in future")
    private LocalDate checkOutDate;
    
    private String status;

    // Getter & Setter
    /*public  int getBookingId() {
        return bookingId;
    }

    public void setBookingId( int bookingId) {
        this.bookingId = bookingId;
    }*/

    public  int getRoomId() {
        return roomId;
    }

    public void setRoomId( int roomId) {
        this.roomId = roomId;
    }

    public  int getCustomerId() {
        return customerId;
    }

    public void setCustomerId( int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
