package com.university.scooterrental.dto.scooter;

import com.university.scooterrental.model.scooter.ScooterStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateScooterRequestDto {
    @NotBlank
    @Size(min = 3, max = 55, message = "The field length should be between 3 and 55 characters")
    private String model;

    @NotNull(message = "Field tariff can`t be null")
    @PositiveOrZero(message = "Field tariff should be greater than 0")
    private BigDecimal tariff;

    @NotNull(message = "Field batteryLevel can`t be null")
    @PositiveOrZero(message = "Field batteryLevel should be greater than 0")
    private Integer batteryLevel;

    @NotNull(message = "Field status can`t be null")
    private ScooterStatus status;
}
