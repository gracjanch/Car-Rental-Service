package com.carrentalservice.exception.type.customer;

public class CustomerAlreadyExists extends RuntimeException {

    public CustomerAlreadyExists() {
        super("Customer already exists!");
    }
}
