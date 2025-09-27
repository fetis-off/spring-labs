package com.university.scooterrental.repository;

import com.university.scooterrental.model.scooter.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {
}
