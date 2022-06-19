package com.carrentalservice.exception.type.rent;

import java.util.NoSuchElementException;

public class RentNotFoundException extends NoSuchElementException {

    public RentNotFoundException() {
        super("Rent not found!");
    }
}
