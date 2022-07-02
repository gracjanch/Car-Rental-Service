package com.carrentalservice.utils.mapper;

import com.carrentalservice.model.entity.Car;
import com.carrentalservice.model.request.CarRequest;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public static Car map(final CarRequest carRequest) {
        return Car.builder()
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .seats(carRequest.getSeats())
                .regNumber(carRequest.getRegNumber())
                .availability(carRequest.getAvailability())
                .productionYear(carRequest.getProductionYear())
                .pricePerHour(carRequest.getPricePerHour())
                .build();
    }
}
