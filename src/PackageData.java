import java.io.Serializable;
import java.util.ArrayList;

public class PackageData implements Serializable {
    private String dataType, operationType;
    private Aircraft aircraft;
    private City city;
    private Flight flight;
    private  Ticket ticket;
    private User user;
    private ArrayList elemOfAirport;

    public PackageData() {
    }

    public PackageData(String dataType, String operationType) {
        this.dataType = dataType;
        this.operationType = operationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public ArrayList getElemOfAirport() {
        return elemOfAirport;
    }

    public void setElemOfAirport(ArrayList elemOfAirport) {
        this.elemOfAirport = elemOfAirport;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
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
}
