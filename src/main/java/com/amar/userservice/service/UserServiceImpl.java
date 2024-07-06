package com.amar.userservice.service;

import com.amar.userservice.model.Token;
import com.amar.userservice.model.User;
import com.amar.userservice.repository.TokenRepository;
import com.amar.userservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenRepository tokenRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseGet(User::new);
    }

    @Override
    public User addUser(User user) {
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {

        Optional<User> optionalExistingUser = userRepository.findById(id);

        if(optionalExistingUser.isPresent()){
            User existingUser = optionalExistingUser.get();
            existingUser.setName(user.getName());
            //existingUser.setPhone(user.getPhone());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with id: "+ id + " not found");
        }
    }

    @Override
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User Deleted";
    }

    @Override
    public User signup(String email, String fullName, String password) {
        User user = new User();
        user.setName(fullName);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            //throw user doesn't exist exception
        }

        User user = optionalUser.get();

        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            //throw password not matching exception

        }

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);
        Date date = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token  = new Token();
        token.setUser(user);
        token.setExpiryAt(date);
        token.setValue(RandomStringUtils.randomAlphabetic(128));

        return tokenRepository.save(token);
    }

    @Override
    public void logout(String token) {

         Optional<Token> optionalToken1 = tokenRepository.findByValueAndIsDeleted(token, false);
         if(optionalToken1.isEmpty()){
             //throw token not found exception or already expired exception
         }
         Token token1 = optionalToken1.get();
         token1.setDeleted(true);
         tokenRepository.save(token1);
    }
}
