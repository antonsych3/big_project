import java.io.Serializable;

public class Ticket implements Serializable {
    private Long id;
    private Flight flight;
    private String name, surname, passportNumber, type;

    public Ticket() {
    }

    public Ticket(Long id) {
        this.id = id;
    }

    public Ticket(Long id, Flight flight, String name,
                  String surname, String passportNumber, String type) {
        this.id = id;
        this.flight = flight;
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return flight +
                ", name: " + name +
                ", surname: " + surname +
                ", passportNumber: " + passportNumber;
    }
}
