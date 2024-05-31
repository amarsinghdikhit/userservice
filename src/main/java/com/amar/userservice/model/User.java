package com.amar.userservice.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseModel{

    private String email;

    private String username;
    
    private String password;

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    private String phone;
}

@Embeddable
@Getter
@Setter
class Name {

    private String firstname;

    private String lastname;
}

@Embeddable
@Getter
@Setter
class Address {

    private String city;

    private String street;

    private int number;

    private String zipcode;

    @Embedded
    private Geolocation geolocation;
}

@Embeddable
@Getter
@Setter
class Geolocation {

    private String lat;

    private String lon;
}
