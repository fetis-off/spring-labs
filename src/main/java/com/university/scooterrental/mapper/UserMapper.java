package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.user.UserDetailedDto;
import com.university.scooterrental.dto.user.UserRegistrationRequestDto;
import com.university.scooterrental.dto.user.UserResponseDto;
import com.university.scooterrental.dto.user.UserUpdateRequestDto;
import com.university.scooterrental.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toUserResponseDto(User user);

    User toUser(UserRegistrationRequestDto userRegistrationRequestDto);

    UserDetailedDto toUserDetailedDto(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequestDto userUpdateRequestDto);
}

