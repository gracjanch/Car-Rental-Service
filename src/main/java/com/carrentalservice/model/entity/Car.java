package com.carrentalservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "car")
@Table(
        name = "car",
        uniqueConstraints = @UniqueConstraint(
                name = "car_registration_number_unique",
                columnNames = "registration_number"))
public class Car {

    private static final int MIN_PRODUCTION_YEAR = 1940;
    private static final int MAX_PRODUCTION_YEAR = LocalDateTime.now().getYear();

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

    @NotBlank(message = "Brand can not be blank or empty")
    @Column(
            name = "brand",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String brand;

    @NotBlank(message = "Model can not be blank or empty")
    @Column(
            name = "model",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String model;

    @Positive(message = "Number of seats is not positive")
    @Column(
            name = "number_of_seats",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private Integer seats;

    @Pattern(
            regexp = "([A-Z]{3}|[A-Z]{2})\\s([A-Z0-9]{5}|[A-Z0-9]{4})",
            message = "Registration number have to match to format: " +
                    "2 or 3 letters nad 4 or 5 letters or numbers"
    )
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

    @Pattern(
            regexp = "([1]|[2])[0-9]{3}",
            message = "Production year have to match to format: 1234"
    )
    @Column(
            name = "production_year",
            nullable = false
    )
    private Long productionYear;

    @Positive(message = "Price per hour is not positive")
    @Pattern(
            regexp = "[1-9][0-9]",
            message = "Price per hour have to match to format: \"11\""
    )
    @Column(
            name = "price_per_hour",
            nullable = false
    )
    private Long pricePerHour;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.REMOVE
    )
    private List<Rent> rentList;
}
