package com.lab.lab_management.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab.lab_management.model.User;
import com.lab.lab_management.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User Register(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        try{
             return userRepository.findByEmail((email));
        }
        catch(Exception ex){
           throw new RuntimeException(
                    "Failed to findByEmail: " + ex.getMessage(), ex);
        }
       
    }

    public Optional<User> login(String email, String password) {
        try{
            Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
        }
        catch(Exception ex){
            throw new RuntimeException(
                    "Failed to login: " + ex.getMessage(), ex);
        }
    }
}
