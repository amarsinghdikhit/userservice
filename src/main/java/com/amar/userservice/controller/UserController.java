package com.amar.userservice.controller;

import com.amar.userservice.dtos.LoginRequestDto;
import com.amar.userservice.dtos.LogoutRequestDto;
import com.amar.userservice.dtos.SignupRequestDto;
import com.amar.userservice.model.Token;
import com.amar.userservice.model.User;
import com.amar.userservice.service.UserService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto requestDto){

        return userService.login(requestDto.getEmail(), requestDto.getPassword());
    }


    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequestDto requestDto){
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String name = requestDto.getName();

        return userService.signup(email, name, password);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto){
        userService.logout(requestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public User validateToken(@PathVariable @NonNull String token){
        return userService.validateToken(token);
    }
}
