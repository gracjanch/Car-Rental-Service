package com.carrentalservice.exception.type.customer;

import java.util.NoSuchElementException;

public class CustomerNotFoundException extends NoSuchElementException {

    public CustomerNotFoundException() {
        super("Customer not found!");
    }
}
