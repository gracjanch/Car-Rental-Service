package com.carrentalservice.service;

import com.carrentalservice.exception.type.car.CarNotFoundException;
import com.carrentalservice.model.entity.Car;
import com.carrentalservice.model.request.CarRequest;
import com.carrentalservice.repository.CarRepository;
import com.carrentalservice.utils.mapper.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Car getById(final Long carId) {
        return getCarByIdFromDb(carId);
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car create(final CarRequest carRequest) {
        Car car = CarMapper.map(carRequest);
        return carRepository.save(car);
    }

    public Car updateById (final Long carId, final CarRequest carRequest) {
        final Car carFromDb = getCarByIdFromDb(carId);

        carFromDb.setBrand(carRequest.getBrand());
        carFromDb.setModel(carRequest.getModel());
        carFromDb.setSeats(carRequest.getSeats());
        carFromDb.setAvailability(carRequest.getAvailability());
        carFromDb.setRegNumber(carRequest.getRegNumber());
        return carRepository.save(carFromDb);
    }

    public void removeById(final Long carId) {
        carRepository.deleteById(carId);
    }

    private Car getCarByIdFromDb(final Long carId) {
        final Optional<Car> carFromDb = carRepository.findById(carId);
        return carFromDb.orElseThrow(CarNotFoundException::new);
    }
}
