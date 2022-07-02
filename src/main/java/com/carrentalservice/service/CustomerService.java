package com.carrentalservice.service;

import com.carrentalservice.exception.type.customer.CustomerNotFoundException;
import com.carrentalservice.model.entity.Car;
import com.carrentalservice.model.entity.Customer;
import com.carrentalservice.model.request.CustomerRequest;
import com.carrentalservice.repository.CustomerRepository;
import com.carrentalservice.utils.mapper.CustomerMapper;
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

    public Customer create(final CustomerRequest customerRequest) {
        Customer customer = CustomerMapper.map(customerRequest);
        return customerRepository.save(customer);
    }

    public Customer updateById (final Long customerId, final CustomerRequest customerRequest) {
        final Customer customerFromDb = getCustomerByIdFromDb(customerId);

        customerFromDb.setName(customerRequest.getName());
        customerFromDb.setLastName(customerRequest.getLastName());
        customerFromDb.setDocType(customerRequest.getDocType());
        customerFromDb.setDocNumber(customerRequest.getDocNumber());
        return customerRepository.save(customerFromDb);
    }

    public void removeById(final Long customerId) {
        customerRepository.deleteById(customerId);
    }

    private Customer getCustomerByIdFromDb(final Long customerId) {
        final Optional<Customer> customerFromDb = customerRepository.findById(customerId);
        return customerFromDb.orElseThrow(CustomerNotFoundException::new);
    }
}
