package com.carrentalservice.exception.type.rent;

public class RentCreationException extends RuntimeException{

    public RentCreationException() {
        super("Rent can not be created!");
    }
}
