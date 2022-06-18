package com.carrentalservice.repository;

import com.carrentalservice.model.entity.Car;
import com.carrentalservice.model.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findRentByCar(Car car);
}
