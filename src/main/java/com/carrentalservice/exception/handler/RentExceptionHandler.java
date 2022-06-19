package com.carrentalservice.exception.handler;

import com.carrentalservice.exception.Error;
import com.carrentalservice.exception.type.rent.RentCreationException;
import com.carrentalservice.exception.type.rent.RentNotFoundException;
import com.carrentalservice.exception.type.rent.RentPeriodNotFree;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

@RestControllerAdvice
public class RentExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RentNotFoundException.class)
    public Error handleRentNotFoundException(final RentNotFoundException exception) {
        return Error.builder()
                .timestamp(LocalDateTime.now())
                .httpCode(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .errorMessage(exception.getMessage())
                .fieldErrors(Collections.emptyList())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RentPeriodNotFree.class)
    public Error handleRentPeriodNotFree(final RentPeriodNotFree exception) {
        return Error.builder()
                .timestamp(LocalDateTime.now())
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .errorMessage(exception.getMessage())
                .fieldErrors(Collections.emptyList())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RentCreationException.class)
    public Error handleRentCreationException(final RentCreationException exception) {
        return Error.builder()
                .timestamp(LocalDateTime.now())
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .errorMessage(exception.getMessage())
                .fieldErrors(Collections.emptyList())
                .build();
    }
}
