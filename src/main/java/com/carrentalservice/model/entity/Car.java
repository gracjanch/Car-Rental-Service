package com.carrentalservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "car")
@Table(name = "car")
public class Car {

    @Id
    @SequenceGenerator(
            name = "car_sequence",
            sequenceName = "car_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_sequence"
    )
    @Column(
            name = "car_id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "brand",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String brand;

    @Column(
            name = "model",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String model;

    @Column(
            name = "number_of_seats",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private Integer seats;

    @Column(
            name = "registration_number",
            nullable = false
    )
    private String regNumber;

    @Column(
            name = "availability",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean availability;
}
