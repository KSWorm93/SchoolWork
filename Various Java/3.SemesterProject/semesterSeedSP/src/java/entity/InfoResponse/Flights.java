package entity.InfoResponse;

import javax.persistence.Entity;

public class Flights {
    private String flightID;
    private String date;
    private int numberOfSeats;
    private Number totalPrice;
    private int travelTime;
    private String destination;
    private String origin;
    private String url;
    
    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Number getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Number totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFlightId() {
        return flightID;
    }

    public void setFlightId(String flightId) {
        this.flightID = flightId;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    
}
