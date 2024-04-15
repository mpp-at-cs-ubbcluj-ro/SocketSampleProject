package ro.ubb.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class CarDto implements Serializable {

    private final Long id;
    private final String name;
    private final String manufacturer;
    private final LocalDate productionDate;

    public CarDto(Long id, String name, String manufacturer, LocalDate productionDate) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", productionDate=" + productionDate +
                '}';
    }
}
