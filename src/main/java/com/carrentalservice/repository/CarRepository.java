package com.carrentalservice.repository;

import com.carrentalservice.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByIdAndAvailabilityEquals(Long carId, boolean isAvailable);
}
