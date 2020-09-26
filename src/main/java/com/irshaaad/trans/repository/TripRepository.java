package com.irshaaad.trans.repository;

import com.irshaaad.trans.model.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Integer> {
    Trip findTripByTripNumber(String tripNumber);

    @Query(value = "SELECT * FROM trip t WHERE t.take_off_point = :takeOffPoint and t.destination_point = :destinationPoint and date(t.take_off_time) = date(:takeOffTime) and t.available_seats > 0", nativeQuery = true)
    List<Trip> searchAvailableTrips(String takeOffPoint, String destinationPoint, Date takeOffTime);

}
