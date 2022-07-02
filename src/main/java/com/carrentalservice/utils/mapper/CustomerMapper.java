package com.carrentalservice.utils.mapper;

import com.carrentalservice.model.entity.Customer;
import com.carrentalservice.model.request.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public static Customer map(final CustomerRequest customerRequest) {
        return Customer.builder()
                .name(customerRequest.getName())
                .lastName(customerRequest.getLastName())
                .docType(customerRequest.getDocType())
                .docNumber(customerRequest.getDocNumber())
                .build();
    }


}
