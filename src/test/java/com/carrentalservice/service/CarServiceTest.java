package com.carrentalservice.service;

import com.carrentalservice.model.entity.Car;
import com.carrentalservice.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

//    @Mock
//    private CarMapper carMapper;

    @Mock
    private Car car;

    @InjectMocks
    private CarService carService;

    @Test
    void getAllCarsListWhenDbIsNotEmpty() {
        final Car car = new Car();
        car.setBrand("TestB");

        Mockito.when(carRepository.findAll())
                .thenReturn(List.of(car, new Car()));

        final List<Car> resultCarsList = carService.getAll();

        Assertions.assertThat(resultCarsList).hasSize(2);
        Assertions.assertThat(resultCarsList.get(0).getBrand()).isEqualTo("TestB");
    }

    @Test
    void getAllCarListReturnEmptyListWhenNoCarAdded() {
        Mockito.when(carRepository.findAll())
                .thenReturn(Collections.emptyList());

        final List<Car> resultCarsList = carService.getAll();

        Assertions.assertThat(resultCarsList).isEmpty();
    }

}