package com.university.scooterrental.controller;

import com.university.scooterrental.dto.user.UserLoginRequestDto;
import com.university.scooterrental.dto.user.UserLoginResponseDto;
import com.university.scooterrental.dto.user.UserRegistrationRequestDto;
import com.university.scooterrental.dto.user.UserResponseDto;
import com.university.scooterrental.exception.RegistrationException;
import com.university.scooterrental.security.AuthenticationService;
import com.university.scooterrental.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
