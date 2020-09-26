package com.irshaaad.trans.repository;

import com.irshaaad.trans.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    Booking findBookingByBookingNumber(String bookingNumber);
   /* Booking findBookingByTicket(String ticket);*/
    List<Booking> findBookingsByTripTripNumber(String tripNumber);
}
