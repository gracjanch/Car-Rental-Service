package com.carrentalservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
@Table(
        name = "customer",
        uniqueConstraints = @UniqueConstraint(
                name = "customer_document_number_unique",
                columnNames = "document_number"))
public class Customer {

    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @NotBlank(message = "Name can not be blank or empty")
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @NotBlank(message = "Last name can not be blank or empty")
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @NotBlank(message = "Document type can not be blank or empty")
    @Column(
            name = "document_type",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String docType;

    @Pattern(
            regexp = "[a-zA-Z]{3}\\s?[0-9]{6}",
            message = "Document number have to match to format: \"XXX 123456\""
    )
    @Column(
            name = "document_number",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String docNumber;

    @JsonIgnore
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL
    )
    private List<Rent> rentList;
}
