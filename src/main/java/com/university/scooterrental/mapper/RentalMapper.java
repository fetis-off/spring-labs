package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.rental.RentalResponseDto;
import com.university.scooterrental.model.rental.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(source = "rental.user.id", target = "userId")
    @Mapping(source = "rental.scooter.id", target = "scooterId")
    RentalResponseDto toRentalResponseDto(Rental rental);

}
