package com.carrentalservice.controller;

import com.carrentalservice.model.entity.Customer;
import com.carrentalservice.model.entity.Rent;
import com.carrentalservice.model.request.RentRequest;
import com.carrentalservice.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @GetMapping
    public ResponseEntity<List<Rent>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(rentService.getAll());
    }

    @GetMapping("/{rentId}")
    public ResponseEntity<Rent> getById(@PathVariable final Long rentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(rentService.getById(rentId));
    }

    @PostMapping
    public ResponseEntity<Rent> create(@RequestBody final RentRequest rentReq) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rentService.create(rentReq));
    }

    @PutMapping("/{rentId}")
    public ResponseEntity<Rent> updateById(@PathVariable final Long rentId,
                                               @RequestBody final RentRequest rentReq) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rentService.updateById(rentId, rentReq));
    }

    @DeleteMapping("/{rentId}")
    public ResponseEntity<Void> removeById(@PathVariable final Long rentId) {
        rentService.removeById(rentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
