package com.carrentalservice.service;

import com.carrentalservice.model.entity.Car;
import com.carrentalservice.model.entity.Customer;
import com.carrentalservice.model.entity.Rent;
import com.carrentalservice.model.request.RentRequest;
import com.carrentalservice.repository.CarRepository;
import com.carrentalservice.repository.CustomerRepository;
import com.carrentalservice.repository.RentRepository;
import com.carrentalservice.utils.mapper.RentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public Rent getById(final Long rentId) {
        return getRentByIdFromDb(rentId);
    }

    public List<Rent> getAll() {
        return rentRepository.findAll();
    }

    public Rent create(final RentRequest rentRequest) {
        Rent rent = RentMapper.map(rentRequest);
        Car car = carRepository.findByIdAndAvailabilityEquals(
                rent.getCar().getId(), true)
                .orElseThrow();
        getRentForCar(car).forEach(r ->
                isPeriodFree(rentRequest.getStartDateTime(),
                        rentRequest.getEndDateTime(), r));
        rent.setCar(car);
        return rentRepository.save(rent);
    }

    public Rent updateById (final Long rentId, final RentRequest rentRequest) {
        final Rent rentFromDb = getRentByIdFromDb(rentId);
        final Customer customer = customerRepository.findById(rentRequest.getCustomerId())
                .orElseThrow();
        final Car car = carRepository.findById(rentRequest.getCarId())
                        .orElseThrow();
        getRentForCar(car)
                .stream().filter(r -> !r.getId().equals(rentId))
                        .forEach(r ->
                                isPeriodFree(rentRequest.getStartDateTime(),
                                        rentRequest.getEndDateTime(),
                                        r));


        rentFromDb.setStartDateTime(rentRequest.getStartDateTime());
        rentFromDb.setEndDateTime(rentRequest.getEndDateTime());
        rentFromDb.setCost(rentRequest.getCost());
        rentFromDb.setCustomer(customer);
        rentFromDb.setCar(car);
        return rentRepository.save(rentFromDb);
    }

    public void removeById(final Long rentId) {
        rentRepository.deleteById(rentId);
    }

    private Rent getRentByIdFromDb(final Long rentId) {
        final Optional<Rent> rentFromDb = rentRepository.findById(rentId);
        return rentFromDb.orElseThrow();
    }

    private List<Rent> getRentForCar(final Car car) {
        return rentRepository.findRentByCar(car);
    }

    private void isPeriodFree(final LocalDateTime startOfRent,
                              final LocalDateTime endOfRent,
                              final Rent rent) {
        if (isStartDateTimeNotFree(startOfRent, rent) ||
                isEndDateTimeNotFree(endOfRent, rent)) {
            throw new RuntimeException();
        }

    }

    private boolean isStartDateTimeNotFree(final LocalDateTime startOfRent,
                                           final Rent rent) {
        return (startOfRent.isAfter(rent.getStartDateTime()) ||
                startOfRent.isEqual(rent.getStartDateTime())
        ) && (startOfRent.isBefore(rent.getEndDateTime()) ||
                startOfRent.isEqual(rent.getEndDateTime()));
    }

    private boolean isEndDateTimeNotFree(final LocalDateTime endOfRent,
                                         final Rent rent) {
        return (endOfRent.isAfter(rent.getStartDateTime()) ||
                endOfRent.isEqual(rent.getStartDateTime())
        ) && (endOfRent.isBefore(rent.getEndDateTime()) ||
                endOfRent.isEqual(rent.getEndDateTime()));
    }

}
