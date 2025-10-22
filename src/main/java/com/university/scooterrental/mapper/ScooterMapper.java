package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.scooter.CreateScooterRequestDto;
import com.university.scooterrental.dto.scooter.ScooterResponseDto;
import com.university.scooterrental.model.scooter.Scooter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScooterMapper {
    Scooter toScooter(CreateScooterRequestDto requestDto);
    ScooterResponseDto toScooterResponseDto(Scooter scooter);
}

