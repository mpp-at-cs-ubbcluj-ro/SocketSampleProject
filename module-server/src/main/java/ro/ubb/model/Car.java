package ro.ubb.model;

import java.time.LocalDate;

public class Car extends Entity<Long> {
    private final String name;
    private final String manufacturer;
    private final LocalDate productionDate;


    public Car(Long id, String name, String manufacturer, LocalDate productionDate) {
        this(name, manufacturer, productionDate);
        setId(id);
    }

    public Car(String name, String manufacturer, LocalDate productionDate) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.productionDate = productionDate;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", productionDate='" + productionDate + '\'' +
                '}';
    }
}
