package com.university.scooterrental.dto.rental;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class RentalResponseDto {
  private Long id;
  private Long userId;
  private Long scooterId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private BigDecimal price;
}
