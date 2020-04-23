import java.io.Serializable;

public class City implements Serializable {
    private static final long serialVersionUID = 8799585939008193594L;
    private Long id;
    private String name, country, shortName;

    public City() {
    }

    public City(Long id) {
        this.id = id;
    }

    public City(Long id, String name, String country, String shortName) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.shortName = shortName;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}


