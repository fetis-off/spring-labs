package com.university.scooterrental.dto.rental;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalRequestDto {
    @NotNull(message = "scooterId should can not be null")
    @Positive(message = "scooterId should be a positive number")
    private Long scooterId;
}
