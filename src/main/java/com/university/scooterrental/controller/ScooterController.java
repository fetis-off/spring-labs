package com.university.scooterrental.controller;

import com.university.scooterrental.dto.scooter.CreateScooterRequestDto;
import com.university.scooterrental.dto.scooter.ScooterResponseDto;
import com.university.scooterrental.model.scooter.Scooter;
import com.university.scooterrental.service.scooter.ScooterService;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/scooters")
@RequiredArgsConstructor
public class ScooterController {
  private final ScooterService scooterService;

  @GetMapping
  public ResponseEntity<List<ScooterResponseDto>> getAllScooters() {
      return ResponseEntity.ok(scooterService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ScooterResponseDto> getScooterById(@PathVariable Long id) {
    return ResponseEntity.ok(scooterService.findById(id));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ScooterResponseDto> createScooter(@RequestBody @Valid CreateScooterRequestDto requestDto) {
    return ResponseEntity.ok(scooterService.save(requestDto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public void updateScooter(@PathVariable Long id,
                            @RequestBody CreateScooterRequestDto requestDto) {
    scooterService.update(id, requestDto);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteScooter(@PathVariable Long id) {
    scooterService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
