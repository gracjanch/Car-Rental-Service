package com.carrentalservice.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class RentRequest {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Long cost;
    private final Long carId;
    private final Long customerId;
}
