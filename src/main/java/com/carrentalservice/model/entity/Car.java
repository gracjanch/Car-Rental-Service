package com.carrentalservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    private static final int MAX_PRODUCTION_YEAR = 2022;
    private static final int MIN_PRICE_PER_HOUR = 1;
    private static final int MAX_PRICE_PER_HOUR = 200;

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
            name = "id",
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

    @Min(
            value = MIN_PRODUCTION_YEAR,
            message = "Minimum production year is " + MIN_PRODUCTION_YEAR
    )
    @Max(
            value = MAX_PRODUCTION_YEAR,
            message = "Maximum production year is " + MAX_PRODUCTION_YEAR
    )
    @Column(
            name = "production_year",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private Integer productionYear;

    @Min(
            value = MIN_PRICE_PER_HOUR,
            message = "Minimum price per hour is " + MIN_PRICE_PER_HOUR
    )
    @Max(
            value = MAX_PRODUCTION_YEAR,
            message = "Maximum price per hour is " + MAX_PRICE_PER_HOUR
    )
    @Column(
            name = "price_per_hour",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private Integer pricePerHour;

    @JsonIgnore
    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.REMOVE
    )
    private List<Rent> rentList;
}
