package com.university.scooterrental.repository;

import com.university.scooterrental.model.scooter.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {
    boolean existsByModel(String model);
}
