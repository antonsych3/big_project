import java.io.Serializable;

public class Aircraft implements Serializable {
    private Long id;
    private String name, model;
    private int businessCapacity, economCapacity;


    public Aircraft() {
    }

    public Aircraft(Long id) {
        this.id = id;
    }

    public Aircraft(Long id, String name, String model, int businessCapacity, int economCapacity) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.businessCapacity = businessCapacity;
        this.economCapacity = economCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBusinessCapacity() {
        return businessCapacity;
    }

    public void setBusinessCapacity(int businessCapacity) {
        this.businessCapacity = businessCapacity;
    }

    public int getEconomCapacity() {
        return economCapacity;
    }

    public void setEconomCapacity(int economCapacity) {
        this.economCapacity = economCapacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + model;
    }
}
