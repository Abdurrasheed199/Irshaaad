package com.irshaaad.trans.controller;

import com.irshaaad.trans.model.Booking;
import com.irshaaad.trans.model.Passenger;
import com.irshaaad.trans.model.Trip;
import com.irshaaad.trans.repository.BookingRepository;
import com.irshaaad.trans.repository.PassengerRepository;
import com.irshaaad.trans.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;

  @Controller
  public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PassengerRepository passengerRepository;



    @RequestMapping(value = "/bookings/list", method = RequestMethod.GET)
    public String bookings(Model model){
      model.addAttribute("bookings", bookingRepository.findAll());
      return "booking/list";
    }

    @RequestMapping(value = "/bookings/bookTrip/{id}", method = RequestMethod.GET)
    public String create(@PathVariable("id") int id, Model model) {

      model.addAttribute("trip", tripRepository.findById(id).get());
      return "booking/bookTrip";
    }

    public String generateUniqueId() {
      Random rand = new Random(); //instance of random class
      //generate random values from 0-100000
      int num = rand.nextInt(100000);
      String uniqueId = ("00" + Integer.toString(num));
      return uniqueId;
    }

    @RequestMapping(value = "/bookings/process", method = RequestMethod.POST)
    public String processBooking(Model model, @RequestParam int id, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String email, @RequestParam String phone, @RequestParam String address, @RequestParam int age) {

      Trip trip = tripRepository.findById(id).get();
      int availableSeats = trip.getAvailableSeats();
      Passenger passenger = new Passenger(lastName, firstName, email, phone, address, age);
      passengerRepository.save(passenger);

      long millis = System.currentTimeMillis();
      Date bookingDate = new Date(millis);

      Booking b = null; String bookingNumber = " ";
      do{
        bookingNumber = generateUniqueId();
        b = bookingRepository.findBookingByBookingNumber(bookingNumber);
      }while (b != null);

      Booking booking = new Booking(bookingNumber, trip, passenger, bookingDate);
      bookingRepository.save(booking);

      trip.setAvailableSeats(availableSeats - 1);
      tripRepository.save(trip);

      model.addAttribute("trip", trip);
      model.addAttribute("booking", booking);
      model.addAttribute("passenger", passenger);
      model.addAttribute("success", "Thank you for travling with us, bellow is the details of your booking. A copy of this has been sent to your email...");

      return "booking/success";
    }

    @RequestMapping(value = "/bookings/edit/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") int id, Model model) {

      model.addAttribute("booking", bookingRepository.findById(id).get());
      return "booking/edit";
    }

    @RequestMapping(value = "/bookings/update", method = RequestMethod.POST)
    public String updateBooking(Model model, @RequestParam int id, @RequestParam String bookingNumber) throws ParseException {

      Booking booking = bookingRepository.findById(id).get();

      booking.setBookingNumber(bookingNumber);

      bookingRepository.save(booking);

      return "redirect:/bookings/list";
    }

    @RequestMapping(value = "/bookings/delete/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") int id, Model model) {

      Booking booking = bookingRepository.findById(id).get();

      bookingRepository.delete(booking);
      return "redirect:/bookings/list";
    }
  }



