package com.university.scooterrentservice.model.scooter;

import com.university.scooterrentservice.model.rental.Rental;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private BigDecimal tariff;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer batteryLevel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScooterStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scooter")
    private List<Rental> rentals = new ArrayList<>();

}
