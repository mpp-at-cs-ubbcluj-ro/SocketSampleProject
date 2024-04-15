package ro.ubb.mapper;

import ro.ubb.dto.CarDto;
import ro.ubb.model.Car;

import java.util.List;

public class CarMapper {

    public static Car toEntity(CarDto carDto) {
        return new Car(carDto.getId(), carDto.getName(), carDto.getManufacturer(), carDto.getProductionDate());
    }

    public static CarDto toDto(Car car) {
        return new CarDto(car.getId(), car.getName(), car.getManufacturer(), car.getProductionDate());
    }

    public static List<CarDto> toDtos(List<Car> carList) {
        return carList.stream().map(CarMapper::toDto).toList();
    }
}
