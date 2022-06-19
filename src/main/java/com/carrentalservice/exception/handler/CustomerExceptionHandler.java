package com.carrentalservice.exception.handler;

import com.carrentalservice.exception.Error;
import com.carrentalservice.exception.type.customer.CustomerAlreadyExists;
import com.carrentalservice.exception.type.customer.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public Error handleCustomerNotFoundException(final CustomerNotFoundException exception) {
        return Error.builder()
                .timestamp(LocalDateTime.now())
                .httpCode(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .errorMessage(exception.getMessage())
                .fieldErrors(Collections.emptyList())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(CustomerAlreadyExists.class)
    public Error handleCustomerAlreadyExists(final CustomerAlreadyExists exception) {
        return Error.builder()
                .timestamp(LocalDateTime.now())
                .httpCode(HttpStatus.NOT_ACCEPTABLE.value())
                .error(HttpStatus.NOT_ACCEPTABLE.name())
                .errorMessage(exception.getMessage())
                .fieldErrors(Collections.emptyList())
                .build();
    }
}
