package com.carrentalservice.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "Name can not be blank or empty")
    private String name;

    @NotBlank(message = "Last name can not be blank or empty")
    private String lastName;

    @NotBlank(message = "Document type can not be blank or empty")
    private String docType;

    @Pattern(
            regexp = "[a-zA-Z]{3}\\s?[0-9]{6}",
            message = "Document number have to match to format: \"XXX 123456\""
    )
    private String docNumber;

}
