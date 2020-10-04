package com.irshaaad.trans.controller;

import com.irshaaad.trans.model.Bus;
import com.irshaaad.trans.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BusController {

    @Autowired
    private BusRepository busRepository;
    @RequestMapping(value = "/buses/list", method = RequestMethod.GET)
    public String buses(Model model){
        model.addAttribute("buses", busRepository.findAll());
//        model.addAttribute("message", "Thank you for travelling with us...");
        return "bus/list";
    }

    @RequestMapping(value = "/buses/create", method = RequestMethod.GET)
    public String create(Model model) {
        return "bus/create";
    }

    @RequestMapping(value = "/buses/add", method = RequestMethod.POST)
    public String add(Model model, @RequestParam String name,@RequestParam String type, @RequestParam String seatNumber, @RequestParam String registrationNumber, @RequestParam int capacity) {

        Bus bus = new Bus(name, type, seatNumber, registrationNumber, capacity);
        busRepository.save(bus);
        //model.addAttribute("message", "The Bus was created successfully...");
        return "redirect:/buses/list";
    }

    @RequestMapping(value = "/buses/edit/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        //Bus bus = busRepository.findById(id);

        model.addAttribute("bus", busRepository.findById(id).get());
        return "bus/edit";
    }

    @RequestMapping(value = "/buses/update", method = RequestMethod.POST)
    public String updateBus(Model model, @RequestParam int id, @RequestParam String name, @RequestParam String type, @RequestParam String seatNumber, @RequestParam String registrationNumber) {

        Bus bus= busRepository.findById(id).get();
        bus.setName(name);
        bus.setType(type);
        bus.setSeatNumber(seatNumber);
        bus.setRegistrationNumber(registrationNumber);
        busRepository.save(bus);

        return "redirect:/buses/list";
    }

    @RequestMapping(value = "/buses/delete/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") int id, Model model) {

        Bus bus = busRepository.findById(id).get();

        busRepository.delete(bus);
        return "redirect:/buses/list";
    }

}
