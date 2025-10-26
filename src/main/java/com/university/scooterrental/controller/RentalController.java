package com.university.scooterrental.controller;

import com.university.scooterrental.dto.rental.RentalRequestDto;
import com.university.scooterrental.dto.rental.RentalResponseDto;
import com.university.scooterrental.model.user.User;
import com.university.scooterrental.service.rental.RentalService;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RentalResponseDto> getAllRentals() {
        return rentalService.findAll();
    }

    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(@PathVariable Long id) {
        return rentalService.findById(id);
    }

    @PostMapping
    public RentalResponseDto createRental(@AuthenticationPrincipal User user,
                                          @RequestBody @Valid RentalRequestDto requestDto) {
        return rentalService.save(user, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/end")
    public RentalResponseDto endRental(@PathVariable Long id) {
        return rentalService.endRental(id);
    }
}
