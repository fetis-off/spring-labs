package com.university.scooterrental.controller;

import com.university.scooterrental.dto.rental.RentalRequestDto;
import com.university.scooterrental.dto.rental.RentalResponseDto;
import com.university.scooterrental.service.rental.RentalService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {
  private final RentalService rentalService;

  @GetMapping
  public List<RentalResponseDto> getAllRentals() {
    return rentalService.findAll();
  }

  @GetMapping("/{id}")
  public RentalResponseDto getRentalById(@PathVariable Long id) {
    return rentalService.findById(id);
  }

  @PostMapping
  public RentalResponseDto createRental(@RequestBody RentalRequestDto RentalRequestDto) {
    return rentalService.save(RentalRequestDto);
  }

  @PutMapping("/{id}")
  public RentalResponseDto updateRental(@PathVariable Long id, @RequestBody RentalRequestDto RentalRequestDto) {
    return rentalService.update(id, RentalRequestDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
    rentalService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
