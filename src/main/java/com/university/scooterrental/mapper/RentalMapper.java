package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.payment.PaymentDto;
import com.university.scooterrental.dto.rental.RentalRequestDto;
import com.university.scooterrental.dto.rental.RentalResponseDto;
import com.university.scooterrental.model.payment.Payment;
import com.university.scooterrental.model.rental.Rental;
import com.university.scooterrental.model.scooter.Scooter;
import com.university.scooterrental.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

  public RentalResponseDto toDto(Rental rental) {
    if (rental == null) return null;

    return RentalResponseDto.builder()
            .id(rental.getId())
            .userId(rental.getUser() != null ? rental.getUser().getId() : null)
            .scooterId(rental.getScooter() != null ? rental.getScooter().getId() : null)
            .startTime(rental.getStartTime())
            .endTime(rental.getEndTime())
            .price(rental.getPrice())
            .build();
  }

  public Rental toEntity(RentalRequestDto dto) {
    if (dto == null) return null;

    Rental rental = new Rental();

    if (dto.getUserId() != null) {
      User user = new User();
      user.setId(dto.getUserId());
      rental.setUser(user);
    }

    if (dto.getScooterId() != null) {
      Scooter scooter = new Scooter();
      scooter.setId(dto.getScooterId());
      rental.setScooter(scooter);
    }

    rental.setStartTime(dto.getStartTime());
    rental.setEndTime(dto.getEndTime());

    return rental;
  }
}
