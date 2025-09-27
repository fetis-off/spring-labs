package com.university.scooterrental.controller;

import com.university.scooterrental.dto.scooter.ScooterDto;
import com.university.scooterrental.model.scooter.Scooter;
import com.university.scooterrental.service.scooter.ScooterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scooters")
@RequiredArgsConstructor
public class ScooterController {
  private final ScooterService scooterService;

  @GetMapping
  public List<ScooterDto> getAllScooters() {
    return scooterService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ScooterDto> getScooterById(@PathVariable Long id) {
    return scooterService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ScooterDto> createScooter(@RequestBody Scooter scooter) {
    return ResponseEntity.ok(scooterService.save(scooter));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<ScooterDto> updateScooter(@PathVariable Long id, @RequestBody Scooter scooterDetails) {
    return ResponseEntity.ok(scooterService.update(id, scooterDetails));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteScooter(@PathVariable Long id) {
    scooterService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
