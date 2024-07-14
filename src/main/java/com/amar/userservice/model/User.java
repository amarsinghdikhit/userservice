package com.amar.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{

    private String name;

    private String email;

    private String username;
    
    private String hashedPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    private Boolean isEmailVerified;
}
