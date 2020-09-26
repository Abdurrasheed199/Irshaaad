package com.irshaaad.trans;

import com.irshaaad.trans.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransApplication implements CommandLineRunner {
    @Autowired
    private BusRepository busRepository;

    public static void main(String[] args) {
        SpringApplication.run(TransApplication.class, args);
    }


    public void run(String... args) throws Exception {
        /*if(aircraftRepository.findAircraftByRegistrationNumber("AB1234")==null){
            Aircraft aircraft = new Aircraft( "xyz", "AB1234", "bOEING", 58);
            aircraftRepository.save(aircraft);
        }*/
    }
}
