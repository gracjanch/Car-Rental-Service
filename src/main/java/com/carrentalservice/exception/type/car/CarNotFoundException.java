package com.carrentalservice.exception.type.car;

import java.util.NoSuchElementException;

public class CarNotFoundException extends NoSuchElementException {

    public CarNotFoundException() {
        super("Car not found!");
    }
}
