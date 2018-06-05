package entity.ReservationRequest;

import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightID;
    private String Origin;
    private String Destination;
    @Temporal(TemporalType.TIMESTAMP)
    private Date Date;
    private int FlightTime;
    private int numberOfSeats;
    private String ReserveeName;
    private String ReservePhone;
    private String ReserveeEmail;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String Origin) {
        this.Origin = Origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public int getFlightTime() {
        return FlightTime;
    }

    public void setFlightTime(int FlightTime) {
        this.FlightTime = FlightTime;
    }
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Passengers> Passengers;

    public void addPassenger(Passengers passenger) {
        Passengers.add(passenger);
    }
    
    public List<Passengers> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<Passengers> Passengers) {
        this.Passengers = Passengers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getReserveeName() {
        return ReserveeName;
    }

    public void setReserveeName(String ReserveeName) {
        this.ReserveeName = ReserveeName;
    }

    public String getReservePhone() {
        return ReservePhone;
    }

    public void setReservePhone(String ReservePhone) {
        this.ReservePhone = ReservePhone;
    }

    public String getReserveeEmail() {
        return ReserveeEmail;
    }

    public void setReserveeEmail(String ReserveeEmail) {
        this.ReserveeEmail = ReserveeEmail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Reservation[ id=" + id + " ]";
    }

}
