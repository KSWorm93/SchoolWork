package entity.InfoResponse;

import java.util.List;

public class Airline {
    private String airline;
    private List<Flights> flights;
    
    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public List<Flights> getFlights() {
        return flights;
    }

    public void setFlights(List<Flights> flights) {
        this.flights = flights;
    }
    
    public Flights addFlight(Flights flight) {
        flights.add(flight);
        return flight;
    }
}
