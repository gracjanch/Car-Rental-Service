package com.carrentalservice.exception.type.rent;

public class RentPeriodNotFree extends RuntimeException {

    public RentPeriodNotFree() {
        super("Rent period is not free!");
    }
}
