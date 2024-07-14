package com.amar.userservice.service;

import com.amar.userservice.dtos.SignupResponseDto;
import com.amar.userservice.model.Token;
import com.amar.userservice.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    User addUser(User user);

    User updateUser(Long id, User user);

    String deleteUser(Long id);

    SignupResponseDto signup(String email, String fullName, String password);

    Token login(String email, String password);

    void logout(String token);

    User validateToken(String token);
}
