package com.university.scooterrentservice.model.rental;

import com.university.scooterrentservice.model.payment.Payment;
import com.university.scooterrentservice.model.scooter.Scooter;
import com.university.scooterrentservice.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "rental")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "scooter_id")
    private Scooter scooter;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal price;

}
