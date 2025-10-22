package com.university.scooterrental.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserUpdateBalanceRequestDto {

    @Positive(message = "field balance should be a positive number")
    @NotNull
    private BigDecimal balance;
}
