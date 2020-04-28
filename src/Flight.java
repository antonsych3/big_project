import java.io.Serializable;

public class Flight implements Serializable {
    private Long id;
    private Aircraft aircraft;
    private City departureCity, arrivalCity;
    private String departureTime;
    private int economPrice, businessPrice;

    public Flight() {
    }

    public Flight(Long id) {
        this.id = id;
    }

    public Flight(Long id, Aircraft aircraft,
                  City departureCity, City arrivalCity, String departureTime,
                  int economPrice, int businessPrice) {
        this.id = id;
        this.aircraft = aircraft;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.economPrice = economPrice;
        this.businessPrice = businessPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public City getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(City departureCity) {
        this.departureCity = departureCity;
    }

    public City getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCityId(City arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getEconomPrice() {
        return economPrice;
    }

    public void setEconomPrice(int economPrice) {
        this.economPrice = economPrice;
    }

    public int getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(int businessPrice) {
        this.businessPrice = businessPrice;
    }

    @Override
    public String toString() {
        return id +
                " - " + aircraft.getName() +
                " - " + departureCity.getName() +
                " - " + arrivalCity.getName();
    }
}
