package com.carrentalservice.exception.type.car;

public class CarAlreadyExists extends RuntimeException {

    public CarAlreadyExists() {
        super("Car already exists!");
    }
}
