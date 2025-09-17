package com.university.scooterrentservice.model.user;

import com.university.scooterrentservice.model.payment.Payment;
import com.university.scooterrentservice.model.rental.Rental;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @Column(nullable = false, unique = true)
    public String email;

    public BigDecimal balance;

    @Enumerated(EnumType.STRING)
    public Role role;

    @OneToMany(mappedBy = "user")
    private List<Rental> rentals = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();
}
