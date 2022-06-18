package com.carrentalservice.service;

import com.carrentalservice.model.entity.Car;
import com.carrentalservice.model.entity.Customer;
import com.carrentalservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getById(final Long customerId) {
        return getCustomerByIdFromDb(customerId);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer create(final Customer customerReq) {
        Customer customer = customerReq;
        return customerRepository.save(customer);
    }

    public Customer updateById (final Long customerId, final Customer customerReq) {
        final Customer customerFromDb = getCustomerByIdFromDb(customerId);

        customerFromDb.setName(customerReq.getName());
        customerFromDb.setLastName(customerReq.getLastName());
        customerFromDb.setDocType(customerReq.getDocType());
        customerFromDb.setDocNumber(customerReq.getDocNumber());
        return customerRepository.save(customerFromDb);
    }

    public void removeById(final Long customerId) {
        customerRepository.deleteById(customerId);
    }

    private Customer getCustomerByIdFromDb(final Long customerId) {
        final Optional<Customer> customerFromDb = customerRepository.findById(customerId);
        return customerFromDb.orElseThrow();
    }
}
