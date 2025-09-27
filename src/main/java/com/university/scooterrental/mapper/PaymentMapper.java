package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.payment.PaymentDto;
import com.university.scooterrental.model.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

  public Payment toEntity(PaymentDto dto) {
    Payment payment = new Payment();
    payment.setId(dto.getId());
    payment.setAmount(dto.getAmount());
    payment.setStatus(dto.getStatus());
    payment.setCreatedAt(dto.getCreatedAt());
    return payment;
  }

  public PaymentDto toDto(Payment payment) {
    return PaymentDto.builder()
            .id(payment.getId())
            .amount(payment.getAmount())
            .status(payment.getStatus())
            .createdAt(payment.getCreatedAt())
            .userId(payment.getUser() != null ? payment.getUser().getId() : null)
            .scooterId(payment.getRental() != null && payment.getRental().getScooter() != null
                    ? payment.getRental().getScooter().getId() : null)
            .build();
  }

}
