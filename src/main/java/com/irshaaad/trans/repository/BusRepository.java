package com.irshaaad.trans.repository;

import com.irshaaad.trans.model.Bus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BusRepository extends CrudRepository<Bus, Integer> {
    Bus findBusByRegistrationNumber(String registrationNumber);
    List<Bus> findBusByCapacityGreaterThanEqual(int capacity);

}
