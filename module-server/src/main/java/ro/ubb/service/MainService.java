package ro.ubb.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.ubb.dto.CarDto;
import ro.ubb.mapper.CarMapper;
import ro.ubb.model.Car;
import ro.ubb.repository.CarRepository;

import java.util.Collections;
import java.util.List;

public class MainService {

    private static final Logger LOG = LogManager.getLogger(MainService.class);

    private final CarRepository carRepository;

    public MainService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void saveCar(CarDto carDto) {
        LOG.info("saveCar - entered with carDto {}", carDto);
        try {
            Car car = CarMapper.toEntity(carDto);
            Long idOfSavedCar = carRepository.save(car);
            LOG.info("saveCar - car was saved with id {}", idOfSavedCar);
        } catch (Exception exception) {
            LOG.error("findAllCars - There has been an error saving the car {}",
                    exception.getMessage());
        }
    }

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars - entered");
        try {
            return CarMapper.toDtos(carRepository.findAll());
        } catch (Exception exception) {
            LOG.error("findAllCars - There has been an error fetching the cars {}",
                    exception.getMessage());
            return Collections.emptyList();
        }
    }
}
