package com.irshaaad.trans.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(unique = true, length = 50)
    private String tripNumber;

    @NotNull
    @ManyToOne
    private Bus bus;

    private Date takeOffTime;
    private Date landingTime;

    private String takeOffPoint;
    private String destinationPoint;
    private double price;
    private int availableSeats;

    public Trip() {

    }

    public Trip(String tripNumber, Bus bus, Date takeOffTime, Date landingTime, String takeOffPoint, String destinationPoint, double price, int availableSeats) {
        this.tripNumber = tripNumber;
        this.bus = bus;
        this.takeOffTime = takeOffTime;
        this.landingTime = landingTime;
        this.takeOffPoint = takeOffPoint;
        this.destinationPoint = destinationPoint;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(String flightNumber) {
        this.tripNumber = tripNumber;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Date getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(Date takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public Date getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(Date landingTime) {
        this.landingTime = landingTime;
    }

    public String getTakeOffPoint() {
        return takeOffPoint;
    }

    public void setTakeOffPoint(String takeOffPoint) {
        this.takeOffPoint = takeOffPoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}




