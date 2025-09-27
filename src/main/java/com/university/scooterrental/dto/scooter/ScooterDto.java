package com.university.scooterrental.dto.scooter;

import com.university.scooterrental.model.scooter.ScooterStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScooterDto {
  private Long id;
  private String model;
  private BigDecimal tariff;
  private Integer quantity;
  private Integer batteryLevel;
  private ScooterStatus status;
}
