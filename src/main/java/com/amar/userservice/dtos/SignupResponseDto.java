package com.amar.userservice.dtos;

import com.amar.userservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SignupResponseDto {

    private String name;

    private String email;

    private List<Role> roles;

    private boolean isEmailVerified;
}
