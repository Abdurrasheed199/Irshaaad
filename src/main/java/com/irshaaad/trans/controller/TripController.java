package com.irshaaad.trans.controller;


import com.irshaaad.trans.model.Booking;
import com.irshaaad.trans.model.Bus;
import com.irshaaad.trans.model.Trip;
import com.irshaaad.trans.repository.BookingRepository;
import com.irshaaad.trans.repository.BusRepository;
import com.irshaaad.trans.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BusRepository busRepository;

    @RequestMapping(value = "/trips/list", method = RequestMethod.GET)
    public String trips(Model model){
        model.addAttribute("trips", tripRepository.findAll());
        return "trip/list";
    }

    @RequestMapping(value = "/trips/availableTrips", method = RequestMethod.GET)
    public String availableTrips(Model model, @RequestParam String takeOffPoint, @RequestParam String destinationPoint, @RequestParam String departure) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date departDate = formatter.parse(departure);
        model.addAttribute("available_trips", tripRepository.searchAvailableTrips(takeOffPoint, destinationPoint, departDate));

        int count=  tripRepository.numberOfAvailableTrips(takeOffPoint, destinationPoint, departDate);
        if (count == 0) {
            model.addAttribute("noTrip","This trip is not available");
        } else {
            model.addAttribute("count",count);
        }
        return "trip/availableTrips";
    }


    @RequestMapping(value = "/trips/create/{id}", method = RequestMethod.GET)
    public String create(@PathVariable("id") int id, Model model) {

        model.addAttribute("bus", busRepository.findById(id).get());
        return "trip/create";
    }

    public String generateUniqueId() {
        Random rand = new Random(); //instance of random class
        //generate random values from 0-100000
        int num = rand.nextInt(100000);
        String uniqueId = ("00" + Integer.toString(num));
        return uniqueId;
    }


    @RequestMapping(value = "/trips/add", method = RequestMethod.POST)
    public String add(Model model, @RequestParam int id /*@RequestParam String tripNumber*/, @RequestParam String takeOffTime, @RequestParam String landingTime, @RequestParam String takeOffPoint, @RequestParam String destinationPoint, @RequestParam double price) throws ParseException {

        Bus bus = busRepository.findById(id).get();
        int availableSeats = bus.getCapacity();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        //System.out.println(takeOffTime);
        Date utilDate = formatter.parse(takeOffTime);

        Date utilDate2 = formatter.parse(landingTime);

        Trip t = null; String tripNumber = " ";
        do{
            tripNumber =generateUniqueId();
            t = tripRepository.findTripByTripNumber(tripNumber);
        }while (t != null);


        Trip trip = new Trip(tripNumber, bus, utilDate, utilDate2, takeOffPoint, destinationPoint, price, availableSeats);
        tripRepository.save(trip);
        return "redirect:/trips/list";
    }

    @RequestMapping(value = "/trips/edit/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") int id, Model model) {

        model.addAttribute("trip", tripRepository.findById(id).get());
        return "trip/edit";
    }

    @RequestMapping(value = "/trips/update", method = RequestMethod.POST)
    public String updateTrip(Model model, @RequestParam int id, @RequestParam String tripNumber, @RequestParam String takeOffTime, @RequestParam String landingTime, @RequestParam String takeOffPoint, @RequestParam String destinationPoint, @RequestParam double price, @RequestParam int availableSeats) throws ParseException {

        Trip trip= tripRepository.findById(id).get();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date takeOff_Time = formatter.parse(takeOffTime);
        trip.setTakeOffTime(takeOff_Time);

        Date landing_Time = formatter.parse(landingTime);
        trip.setLandingTime(landing_Time);

        trip.setAvailableSeats(availableSeats);
        trip.setDestinationPoint(destinationPoint);
        trip.setTripNumber(tripNumber);
        trip.setPrice(price);
        trip.setTakeOffPoint(takeOffPoint);

        tripRepository.save(trip);

        return "redirect:/trips/list";
    }

    @RequestMapping(value = "/trips/delete/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") int id, Model model) {

        Trip trip = tripRepository.findById(id).get();

        tripRepository.delete(trip);
        return "redirect:/trips/list";
    }
}
