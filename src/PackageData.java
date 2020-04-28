import java.io.Serializable;
import java.util.ArrayList;

public class PackageData implements Serializable {
    private Operation operation;
    private User user;
    private ArrayList allAtList;
    private Aircraft aircraft;
    private City city;
    private Flight flight;
    private Ticket ticket;

    public PackageData() {
    }

    public PackageData(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList getAllAtList() {
        return allAtList;
    }

    public void setAllAtList(ArrayList allAtList) {
        this.allAtList = allAtList;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "PackageData{" +
                "operationType=" + operation +
                ", user=" + user +
                ", allAtList=" + allAtList +
                ", aircraft=" + aircraft +
                ", city=" + city +
                ", flight=" + flight +
                ", ticket=" + ticket +
                '}';
    }
}
