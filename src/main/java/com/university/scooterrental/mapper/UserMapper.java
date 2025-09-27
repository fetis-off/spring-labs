package com.university.scooterrental.mapper;

import com.university.scooterrental.dto.user.UserDetailedDto;
import com.university.scooterrental.dto.user.UserRegistrationRequestDto;
import com.university.scooterrental.dto.user.UserResponseDto;
import com.university.scooterrental.dto.user.UserUpdateRequestDto;
import com.university.scooterrental.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserResponseDto toDto(User user) {
    if ( user == null ) {
      return null;
    }

    UserResponseDto userResponseDto = new UserResponseDto();

    if ( user.getId() != null ) {
      userResponseDto.setId( user.getId() );
    }
    if ( user.getEmail() != null ) {
      userResponseDto.setEmail( user.getEmail() );
    }
    if ( user.getFirstName() != null ) {
      userResponseDto.setFirstName( user.getFirstName() );
    }
    if ( user.getLastName() != null ) {
      userResponseDto.setLastName( user.getLastName() );
    }

    return userResponseDto;
  }

  public User toEntity(UserRegistrationRequestDto userRegistrationRequestDto) {
    if ( userRegistrationRequestDto == null ) {
      return null;
    }

    User user = new User();

    if ( userRegistrationRequestDto.getEmail() != null ) {
      user.setEmail( userRegistrationRequestDto.getEmail() );
    }
    if ( userRegistrationRequestDto.getFirstName() != null ) {
      user.setFirstName( userRegistrationRequestDto.getFirstName() );
    }
    if ( userRegistrationRequestDto.getLastName() != null ) {
      user.setLastName( userRegistrationRequestDto.getLastName() );
    }
    if ( userRegistrationRequestDto.getPassword() != null ) {
      user.setPassword( userRegistrationRequestDto.getPassword() );
    }

    return user;
  }

  public UserDetailedDto toFullDto(User user) {
    if ( user == null ) {
      return null;
    }

    UserDetailedDto userDetailedDto = new UserDetailedDto();

    if ( user.getId() != null ) {
      userDetailedDto.setId( user.getId() );
    }
    if ( user.getFirstName() != null ) {
      userDetailedDto.setFirstName( user.getFirstName() );
    }
    if ( user.getLastName() != null ) {
      userDetailedDto.setLastName( user.getLastName() );
    }
    if ( user.getEmail() != null ) {
      userDetailedDto.setEmail( user.getEmail() );
    }
    if ( user.getRole() != null ) {
      userDetailedDto.setRole( user.getRole() );
    }

    return userDetailedDto;
  }

  public void updateUser(User user, UserUpdateRequestDto userUpdateRequestDto) {
    if ( userUpdateRequestDto == null ) {
      return;
    }

    if ( userUpdateRequestDto.getEmail() != null ) {
      user.setEmail( userUpdateRequestDto.getEmail() );
    }
    else {
      user.setEmail( null );
    }
    if ( userUpdateRequestDto.getFirstName() != null ) {
      user.setFirstName( userUpdateRequestDto.getFirstName() );
    }
    else {
      user.setFirstName( null );
    }
    if ( userUpdateRequestDto.getLastName() != null ) {
      user.setLastName( userUpdateRequestDto.getLastName() );
    }
    else {
      user.setLastName( null );
    }
  }
}

