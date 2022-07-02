package com.carrentalservice.utils.mapper;

import com.carrentalservice.model.entity.Car;
import com.carrentalservice.model.entity.Customer;
import com.carrentalservice.model.entity.Rent;
import com.carrentalservice.model.request.RentRequest;
import org.springframework.stereotype.Component;

@Component
public class RentMapper {

    public static Rent map(final RentRequest rentRequest) {
        final Customer customer = Customer.builder()
                .build();
        final Car car = Car.builder()
                .build();
        return Rent.builder()
                .startDateTime(rentRequest.getStartDateTime())
                .endDateTime(rentRequest.getEndDateTime())
                .cost(rentRequest.getCost())
                .customer(customer)
                .car(car)
                .build();
    }
}
