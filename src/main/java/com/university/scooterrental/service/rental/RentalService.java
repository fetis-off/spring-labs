package com.university.scooterrental.service.rental;

import com.university.scooterrental.dto.payment.PaymentDto;
import com.university.scooterrental.dto.rental.RentalRequestDto;
import com.university.scooterrental.dto.rental.RentalResponseDto;
import com.university.scooterrental.mapper.RentalMapper;
import com.university.scooterrental.model.payment.PaymentStatus;
import com.university.scooterrental.model.rental.Rental;
import com.university.scooterrental.model.scooter.Scooter;
import com.university.scooterrental.model.user.User;
import com.university.scooterrental.repository.RentalRepository;
import com.university.scooterrental.repository.ScooterRepository;
import com.university.scooterrental.repository.UserRepository;
import com.university.scooterrental.service.payment.PaymentService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentalService {
  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final ScooterRepository scooterRepository;
  private final RentalMapper rentalMapper;
  private final PaymentService paymentService;

  @Transactional(readOnly = true)
  public List<RentalResponseDto> findAll() {
    return rentalRepository.findAll()
            .stream()
            .map(rentalMapper::toDto)
            .toList();
  }

  @Transactional(readOnly = true)
  public RentalResponseDto findById(Long id) {
    return rentalRepository.findById(id)
            .map(rentalMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Rental not found with id " + id));
  }

  @Transactional
  public RentalResponseDto save(RentalRequestDto rentalRequestDto) {
    Rental rental = rentalMapper.toEntity(rentalRequestDto);

    if (rentalRequestDto.getScooterId() != null) {
      Scooter scooter = scooterRepository.findById(rentalRequestDto.getScooterId())
              .orElseThrow(() -> new RuntimeException("Scooter not found"));
      rental.setScooter(scooter);
      rental.setPrice(calculatePrice(rentalRequestDto.getStartTime(),
              rentalRequestDto.getEndTime(), scooter.getTariff()));
    }

    if (rentalRequestDto.getUserId() != null) {
      User user = userRepository.findById(rentalRequestDto.getUserId())
              .orElseThrow(() -> new RuntimeException("User not found"));
      rental.setUser(user);
    }
    return rentalMapper.toDto(rentalRepository.save(rental));
  }

  @Transactional
  public RentalResponseDto update(Long id, RentalRequestDto rentalDetails) {
    Rental rental = rentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rental not found with id " + id));

    rental.setStartTime(rentalDetails.getStartTime());
    rental.setEndTime(rentalDetails.getEndTime());

    if (rentalDetails.getUserId() != null) {
      User user = new User();
      user.setId(rentalDetails.getUserId());
      rental.setUser(user);
    }

    if (rentalDetails.getScooterId() != null) {
      Scooter scooter = new Scooter();
      scooter.setId(rentalDetails.getScooterId());
      rental.setScooter(scooter);
    }

    return rentalMapper.toDto(rentalRepository.save(rental));
  }

  public void delete(Long id) {
    if (!rentalRepository.existsById(id)) {
      throw new RuntimeException("Rental not found with id " + id);
    }
    rentalRepository.deleteById(id);
  }

  private BigDecimal calculatePrice(LocalDateTime start, LocalDateTime end, BigDecimal tariff) {
    long days = ChronoUnit.DAYS.between(start, end);
    if (days <= 0) {
      throw new IllegalArgumentException("End time must be after start time");
    }

    return tariff.multiply(BigDecimal.valueOf(days));
  }

  private PaymentDto preparePayment(Rental rental) {
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setAmount(rental.getPrice());
    paymentDto.setCreatedAt(LocalDateTime.now());
    paymentDto.setUserId(rental.getUser().getId());
    paymentDto.setScooterId(rental.getScooter().getId());
    paymentDto.setStatus(PaymentStatus.PENDING);
    return paymentDto;
  }
}
