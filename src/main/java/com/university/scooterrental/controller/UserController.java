package com.university.scooterrental.controller;

import com.university.scooterrental.dto.user.UserDetailedDto;
import com.university.scooterrental.dto.user.UserUpdateBalanceRequestDto;
import com.university.scooterrental.dto.user.UserUpdateRequestDto;
import com.university.scooterrental.dto.user.UserUpdateRoleRequestDto;
import com.university.scooterrental.model.user.User;
import com.university.scooterrental.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/role")
    public UserDetailedDto updateRole(@PathVariable Long id,
                                      @RequestBody @Valid UserUpdateRoleRequestDto updateRoleDto) {
        return userService.updateUserRole(id, updateRoleDto);
    }

    @PutMapping("/me")
    public UserDetailedDto updateUserProfile(@AuthenticationPrincipal User user,
                                             @RequestBody @Valid UserUpdateRequestDto updateDto) {
        return userService.updateUserProfile(user.getId(), updateDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailedDto> currentUserProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getCurrentUserProfile(user));
    }

    @PutMapping("/me/balance/top-up")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void topUpBalance(@AuthenticationPrincipal User user,
                             @RequestBody @Valid UserUpdateBalanceRequestDto updateDto) {
        userService.topUpBalance(user.getId(), updateDto);
    }
}
