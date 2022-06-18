package com.carrentalservice.service;

import com.carrentalservice.model.entity.Car;
import com.carrentalservice.repository.CarRepository;
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

    public Car create(final Car carReq) {
        Car car = carReq;
        return carRepository.save(car);
    }

    public Car updateById (final Long carId, final Car carReq) {
        final Car carFromDb = getCarByIdFromDb(carId);

        carFromDb.setBrand(carReq.getBrand());
        carFromDb.setModel(carReq.getModel());
        carFromDb.setSeats(carReq.getSeats());
        carFromDb.setAvailability(carReq.getAvailability());
        carFromDb.setRegNumber(carReq.getRegNumber());
        return carRepository.save(carFromDb);
    }

    public void removeById(final Long carId) {
        carRepository.deleteById(carId);
    }

    private Car getCarByIdFromDb(final Long carId) {
        final Optional<Car> carFromDb = carRepository.findById(carId);
        return carFromDb.orElseThrow();
    }
}
