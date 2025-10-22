package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.rental.RentalRequestDto;
import com.university.scooterrental.dto.rental.RentalResponseDto;
import com.university.scooterrental.model.rental.Rental;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    RentalResponseDto toRentalResponseDto(Rental rental);

    Rental toRental(RentalRequestDto dto);
}
