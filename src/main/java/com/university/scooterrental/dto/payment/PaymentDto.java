package com.university.scooterrental.dto.payment;

import com.university.scooterrental.model.payment.PaymentStatus;
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
public class PaymentDto {
  private Long id;
  private Long userId;
  private Long scooterId;
  private BigDecimal amount;
  private PaymentStatus status;
  private LocalDateTime createdAt;
}
