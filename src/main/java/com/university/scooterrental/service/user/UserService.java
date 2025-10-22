package com.university.scooterrental.service.user;

import com.university.scooterrental.dto.user.*;
import com.university.scooterrental.exception.DataProcessingException;
import com.university.scooterrental.exception.RegistrationException;
import com.university.scooterrental.mapper.UserMapper;
import com.university.scooterrental.model.user.Role;
import com.university.scooterrental.model.user.User;
import com.university.scooterrental.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException("User with email: "
                    + userRegistrationRequestDto.getEmail() + " already exists");
        }
        User user = userMapper.toUser(userRegistrationRequestDto);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }

    public UserDetailedDto getCurrentUserProfile(User user) {
        return userMapper.toUserDetailedDto(user);
    }

    @Transactional
    public UserDetailedDto updateUserRole(Long id, UserUpdateRoleRequestDto updateRoleRequestDto) {
        User user = findUserById(id);
        try {
            user.setRole(Role.valueOf(updateRoleRequestDto.getRole()));
        } catch (IllegalArgumentException e) {
            throw new DataProcessingException("Invalid role with name: "
                    + updateRoleRequestDto.getRole());
        }
        return userMapper.toUserDetailedDto(userRepository.save(user));
    }

    @Transactional
    public UserDetailedDto updateUserProfile(Long userId, UserUpdateRequestDto updateRequestDto) {
        User user = findUserById(userId);
        userMapper.updateUser(user, updateRequestDto);
        return userMapper.toUserDetailedDto(userRepository.save(user));
    }

    @Transactional
    public void topUpBalance(Long userId, UserUpdateBalanceRequestDto requestDto) {
        User user = findUserById(userId);
        BigDecimal newBalance = user.getBalance().add(requestDto.getBalance());
        user.setBalance(newBalance);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id: " + id + " not found")
        );
    }

}
