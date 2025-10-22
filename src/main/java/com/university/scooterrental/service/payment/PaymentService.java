package com.university.scooterrental.service.payment;

import com.university.scooterrental.dto.payment.PaymentDto;
import com.university.scooterrental.mapper.PaymentMapper;
import com.university.scooterrental.model.payment.Payment;
import com.university.scooterrental.model.rental.Rental;
import com.university.scooterrental.model.user.User;
import com.university.scooterrental.repository.PaymentRepository;
import com.university.scooterrental.repository.RentalRepository;
import com.university.scooterrental.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final PaymentRepository paymentRepository;
  private final PaymentMapper paymentMapper;
  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;

  @Transactional
  public PaymentDto save(PaymentDto payment) {
    Payment entity = paymentMapper.toPayment(payment);

    if (payment.getUserId() != null) {
      User user = userRepository.findById(payment.getUserId())
              .orElseThrow(() -> new EntityNotFoundException("User not found"));
      entity.setUser(user);
    }

    if (payment.getScooterId() != null) {
      Rental rental = rentalRepository.findById(payment.getScooterId())
              .orElseThrow(() -> new EntityNotFoundException("Rental not found"));
      entity.setRental(rental);
    }
    return paymentMapper.toPaymentDto(paymentRepository.save(entity));
  }
}
