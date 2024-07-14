package com.amar.userservice.security.models;

import com.amar.userservice.model.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {

    private String authorities;

    public CustomGrantedAuthority(Role role) {
        this.authorities = role.getName();
    }
    @Override
    public String getAuthority() {
        return authorities;
    }
}
