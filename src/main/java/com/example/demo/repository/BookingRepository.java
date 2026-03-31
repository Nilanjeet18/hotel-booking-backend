package com.example.demo.repository;

import com.example.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // ✅ Overlap check
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
           "AND b.status != 'CANCELLED' " +
           "AND b.checkIn < :checkOut " +
           "AND b.checkOut > :checkIn")
    List<Booking> findOverlappingBookings(
            @Param("roomId")   int roomId,
            @Param("checkIn")  LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

    // ✅ Customer ID वरून bookings
    List<Booking> findByCustomerCustomerId(int customerId);

    // ✅ Daily revenue
    @Query("SELECT SUM(b.totalAmount) FROM Booking b " +
           "WHERE b.checkIn = :date AND b.status = 'CONFIRMED'")
    Double getDailyRevenue(@Param("date") LocalDate date);

    // ✅ Monthly count
    @Query("SELECT COUNT(b) FROM Booking b " +
           "WHERE MONTH(b.checkIn) = :month " +
           "AND YEAR(b.checkIn) = :year " +
           "AND b.status = 'CONFIRMED'")
    Long countMonthlyBookings(@Param("month") int month,
                              @Param("year")  int year);

    // ✅ FIX: findByCheckIn() काम करत नाही — @Query वापरा
    @Query("SELECT b FROM Booking b WHERE b.checkIn = :checkIn")
    List<Booking> findByCheckIn(@Param("checkIn") LocalDate checkIn);
    
    void deleteByRoom_RoomId(int roomId);
}