package com.irshaaad.trans.repository;

import com.irshaaad.trans.model.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Integer> {
    Trip findTripByTripNumber(String tripNumber);

    @Query(value = "SELECT * FROM trip t WHERE t.take_off_point = :takeOffPoint and t.destination_point = :destinationPoint and date(t.take_off_time) = date(:takeOffTime) /*and t.available_seats > 0*/", nativeQuery = true)
    List<Trip> searchAvailableTrips(String takeOffPoint, String destinationPoint, Date takeOffTime);

    /*@Query(countByPostId)
    Integer countByPostId(Long post_id);

    final String countByPostId= "SELECT COUNT(ra) FROM Rating ra WHERE ra.post_id = ?1";*/

    @Query(value = "SELECT COUNT(*) FROM trip t WHERE t.take_off_point = :takeOffPoint and t.destination_point = :destinationPoint and date(t.take_off_time) = date(:takeOffTime) /*and t.available_seats > 0*/", nativeQuery = true)
    int numberOfAvailableTrips(String takeOffPoint, String destinationPoint, Date takeOffTime);

}
