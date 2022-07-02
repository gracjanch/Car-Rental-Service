package com.carrentalservice.controller;


import com.carrentalservice.model.entity.Customer;
import com.carrentalservice.model.request.CustomerRequest;
import com.carrentalservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getAll());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getById(@PathVariable final Long customerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getById(customerId));
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody final CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.create(customerRequest));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateById(@PathVariable final Long customerId,
                                          @RequestBody final CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.updateById(customerId, customerRequest));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> removeById(@PathVariable final Long customerId) {
        customerService.removeById(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
