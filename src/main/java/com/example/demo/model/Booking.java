package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @Column(name = "check_in")
    private LocalDate checkIn;      // field name = checkIn

    @Column(name = "check_out")
    private LocalDate checkOut;

    private String status;

    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // ── Getters & Setters ──────────────────────────
    public int getBookingId()           { return bookingId; }
    public void setBookingId(int id)    { this.bookingId = id; }

    public LocalDate getCheckIn()             { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut()              { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public String getStatus()              { return status; }
    public void setStatus(String status)   { this.status = status; }

    public Double getTotalAmount()               { return totalAmount; }
    public void setTotalAmount(Double amount)    { this.totalAmount = amount; }

    public Customer getCustomer()                { return customer; }
    public void setCustomer(Customer customer)   { this.customer = customer; }

    public Room getRoom()             { return room; }
    public void setRoom(Room room)    { this.room = room; }
}