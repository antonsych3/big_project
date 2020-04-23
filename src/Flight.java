import java.io.Serializable;

public class Flight implements Serializable {
    private Long id, aircraftId, departureCityId, arrivalCityId;
    private String departureTime;
    private int economPrice, businessPrice;

    public Flight() {
    }

    public Flight(Long id) {
        this.id = id;
    }

    public Flight(Long id, Long aircraftId, Long departureCityId, Long arrivalCityId, String departureTime, int economPrice, int businessPrice) {
        this.id = id;
        this.aircraftId = aircraftId;
        this.departureCityId = departureCityId;
        this.arrivalCityId = arrivalCityId;
        this.departureTime = departureTime;
        this.economPrice = economPrice;
        this.businessPrice = businessPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public Long getDepartureCityId() {
        return departureCityId;
    }

    public void setDepartureCityId(Long departureCityId) {
        this.departureCityId = departureCityId;
    }

    public Long getArrivalCityId() {
        return arrivalCityId;
    }

    public void setArrivalCityId(Long arrivalCityId) {
        this.arrivalCityId = arrivalCityId;
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
        return "Flight{" +
                "id=" + id +
                ", aircraftId=" + aircraftId +
                ", departureCityId=" + departureCityId +
                ", arrivalCityId=" + arrivalCityId +
                ", departureTime='" + departureTime + '\'' +
                ", economPrice=" + economPrice +
                ", businessPrice=" + businessPrice +
                '}';
    }
}
