package com.irshaaad.trans.repository;

import com.irshaaad.trans.model.Passenger;
import org.springframework.data.repository.CrudRepository;

public interface PassengerRepository extends CrudRepository<Passenger, Integer> {
    Passenger findPassengerByLastNameAndFirstNameAndEmail(String lastName, String firstName, String email);
}
