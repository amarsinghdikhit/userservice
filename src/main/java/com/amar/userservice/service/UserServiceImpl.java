package com.amar.userservice.service;

import com.amar.userservice.model.User;
import com.amar.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
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
            existingUser.setPhone(user.getPhone());
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
}
