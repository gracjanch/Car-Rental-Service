package com.carrentalservice.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class RentRequest {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    @Positive(message = "Cost is not positive")
    private final Integer cost;
    private final Long carId;
    private final Long customerId;
}
