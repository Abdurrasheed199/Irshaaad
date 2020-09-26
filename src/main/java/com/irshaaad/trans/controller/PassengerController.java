package com.irshaaad.trans.controller;

import com.irshaaad.trans.model.Passenger;
import com.irshaaad.trans.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PassengerController {

    @Autowired
    PassengerRepository passengerRepository;

    @RequestMapping(value = "/passengers/list", method = RequestMethod.GET)
    public String passengers(Model model){
        model.addAttribute("passengers", passengerRepository.findAll());
        return "passenger/list";
    }

    @RequestMapping(value = "/passengers/delete/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") int id, Model model) {

        Passenger passenger = passengerRepository.findById(id).get();

        passengerRepository.delete(passenger);
        return "redirect:/passengers/list";
    }

    @RequestMapping(value = "/passengers/edit/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") int id, Model model) {

        model.addAttribute("passenger", passengerRepository.findById(id).get());
        return "passenger/edit";
    }

    @RequestMapping(value = "/passengers/update", method = RequestMethod.POST)
    public String updateFlight(Model model, @RequestParam int id, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String email, @RequestParam String phone, @RequestParam String address, @RequestParam int age) {

        Passenger passenger= passengerRepository.findById(id).get();

        passenger.setLastName(lastName);
        passenger.setFirstName(firstName);
        passenger.setEmail(email);
        passenger.setPhone(phone);
        passenger.setAddress(address);
        passenger.setAge(age);

        passengerRepository.save(passenger);

        return "redirect:/passengers/list";
    }

}
