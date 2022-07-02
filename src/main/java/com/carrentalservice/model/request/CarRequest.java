package com.carrentalservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;


@Getter
@Builder
@AllArgsConstructor
public class CarRequest {

    private static final int MIN_PRODUCTION_YEAR = 1940;
    private static final int MAX_PRODUCTION_YEAR = 2022;
    private static final int MIN_PRICE_PER_HOUR = 1;
    private static final int MAX_PRICE_PER_HOUR = 200;

    @NotBlank(message = "Brand can not be blank or empty")
    private String brand;

    @NotBlank(message = "Model can not be blank or empty")
    private String model;

    @Positive(message = "Number of seats is not positive")
    private Integer seats;

    @Pattern(
            regexp = "([A-Z]{3}|[A-Z]{2})\\s([A-Z0-9]{5}|[A-Z0-9]{4})",
            message = "Registration number have to match to format: " +
                    "2 or 3 letters nad 4 or 5 letters or numbers"
    )
    private String regNumber;

    private Boolean availability;

    @Min(
        value = MIN_PRODUCTION_YEAR,
        message = "Minimum production year is " + MIN_PRODUCTION_YEAR
    )
    @Max(
        value = MAX_PRODUCTION_YEAR,
        message = "Maximum production year is " + MAX_PRODUCTION_YEAR
    )
    private Integer productionYear;

    @Min(
            value = MIN_PRICE_PER_HOUR,
            message = "Minimum price per hour is " + MIN_PRICE_PER_HOUR
    )
    @Max(
            value = MAX_PRICE_PER_HOUR,
            message = "Maximum price per hour is " + MAX_PRICE_PER_HOUR
    )
    private Integer pricePerHour;
}
