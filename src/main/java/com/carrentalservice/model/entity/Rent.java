package com.carrentalservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rent")
@Table(name = "rent")
public class Rent {

    @Id
    @SequenceGenerator(
            name = "rent_sequence",
            sequenceName = "rent_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rent_sequence"
    )
    @Column(
            name = "rent_id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "start_date_time",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime startDateTime;

    @Column(
            name = "end_date_time",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime endDateTime;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "car_id")
    private Car car;
}
