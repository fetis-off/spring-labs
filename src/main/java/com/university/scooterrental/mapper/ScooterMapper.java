package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.scooter.ScooterDto;
import com.university.scooterrental.model.scooter.Scooter;
import org.springframework.stereotype.Component;

@Component
public class ScooterMapper {

  public ScooterDto toDto(Scooter scooter) {
    if (scooter == null) return null;

    return ScooterDto.builder()
            .id(scooter.getId())
            .model(scooter.getModel())
            .tariff(scooter.getTariff())
            .quantity(scooter.getQuantity())
            .batteryLevel(scooter.getBatteryLevel())
            .status(scooter.getStatus())
            .build();
  }

  public Scooter toEntity(ScooterDto dto) {
    if (dto == null) return null;

    Scooter scooter = new Scooter();
    scooter.setId(dto.getId());
    scooter.setModel(dto.getModel());
    scooter.setTariff(dto.getTariff());
    scooter.setQuantity(dto.getQuantity());
    scooter.setBatteryLevel(dto.getBatteryLevel());
    scooter.setStatus(dto.getStatus());

    return scooter;
  }
}

