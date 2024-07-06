package com.amar.userservice.dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String name;
}
