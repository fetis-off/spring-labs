package com.university.scooterrental.dto.user;

import com.university.scooterrental.model.user.Role;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDetailedDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
