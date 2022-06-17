package com.carrentalservice.model.entity;

import java.time.LocalDateTime;

public class Rent {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Customer customer;
    private Car car;
}
