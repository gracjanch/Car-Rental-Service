package com.carrentalservice.controller;

import com.carrentalservice.model.entity.Car;
import com.carrentalservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.getAll());
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getById(@PathVariable final Long carId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.getById(carId));
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody final Car carReq) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.create(carReq));
    }

    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateById(@PathVariable final Long carId,
                                          @RequestBody final Car carReq) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.updateById(carId, carReq));
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> removeById(@PathVariable final Long carId) {
        carService.removeById(carId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
