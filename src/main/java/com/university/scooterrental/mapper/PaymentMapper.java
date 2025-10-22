package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.payment.PaymentDto;
import com.university.scooterrental.model.payment.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  Payment toPayment(PaymentDto dto);
  PaymentDto toPaymentDto(Payment payment);

}
