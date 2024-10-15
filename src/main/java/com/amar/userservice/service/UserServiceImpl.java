package com.amar.userservice.service;

import com.amar.userservice.dtos.SendEmailEventDto;
import com.amar.userservice.dtos.SignupResponseDto;
import com.amar.userservice.model.Token;
import com.amar.userservice.model.User;
import com.amar.userservice.repository.TokenRepository;
import com.amar.userservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    @Autowired
    UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository,
                    KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
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
    public SignupResponseDto signup(String email, String fullName, String password) throws JsonProcessingException {
        User user = new User();
        user.setName(fullName);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setIsEmailVerified(true);
        user = userRepository.save(user);

        SendEmailEventDto emailEventDto = new SendEmailEventDto();
        emailEventDto.setTo(user.getEmail());
        emailEventDto.setFrom("amar@scaler.com");
        emailEventDto.setSubject("Welcome to signup code");
        emailEventDto.setBody("You have been signed up, You can start using our services");

        kafkaTemplate.send("SEND_EMAIL", objectMapper.writeValueAsString(emailEventDto));
        return new SignupResponseDto(user.getName(), user.getEmail(), user.getRoles(), user.getIsEmailVerified());
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

    @Override
    public User validateToken(String token) {
        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeletedAndExpiryAtIsAfter(token, false, new java.sql.Date(System.currentTimeMillis()));
        if(optionalToken.isEmpty()){
            return null;
        }

        return optionalToken.get().getUser();
    }
}
