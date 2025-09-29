package com.university.scooterrental.service.user;

import com.university.scooterrental.dto.user.UserDetailedDto;
import com.university.scooterrental.dto.user.UserRegistrationRequestDto;
import com.university.scooterrental.dto.user.UserResponseDto;
import com.university.scooterrental.dto.user.UserUpdateRequestDto;
import com.university.scooterrental.dto.user.UserUpdateRoleRequestDto;
import com.university.scooterrental.exception.DataProcessingException;
import com.university.scooterrental.exception.RegistrationException;
import com.university.scooterrental.mapper.UserMapper;
import com.university.scooterrental.model.user.Role;
import com.university.scooterrental.model.user.User;
import com.university.scooterrental.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
          throws RegistrationException {
    if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
      throw new RegistrationException("User with email: "
              + userRegistrationRequestDto.getEmail() + " already exists");
    }
    User user = userMapper.toEntity(userRegistrationRequestDto);
    user.setRole(Role.USER);
    user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
    userRepository.save(user);
    return userMapper.toDto(user);
  }

  public UserDetailedDto getCurrentUserProfile(User user) {
    return userMapper.toFullDto(user);
  }

  public UserDetailedDto updateUserRole(Long id, UserUpdateRoleRequestDto updateRoleRequestDto) {
    User user = userRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("User with id: " + id + " not found")
    );
    try {
      user.setRole(Role.valueOf(updateRoleRequestDto.getRole()));
    } catch (IllegalArgumentException e) {
      throw new DataProcessingException("Invalid role with name: "
              + updateRoleRequestDto.getRole());
    }
    return userMapper.toFullDto(userRepository.save(user));
  }

  public UserDetailedDto updateUserProfile(Long userId, UserUpdateRequestDto updateRequestDto) {
    User user = userRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("User with id: " + userId + " not found")
    );
    userMapper.updateUser(user, updateRequestDto);
    return userMapper.toFullDto(userRepository.save(user));
  }

}
