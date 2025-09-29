package com.university.scooterrental.repository;

import com.university.scooterrental.model.rental.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
