package com.irshaaad.trans.repository;


import com.irshaaad.trans.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    Location findByCode(String code);
}
