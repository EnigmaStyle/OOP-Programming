package com.mangahub.user.service;

import java.io.IOException;

import com.mangahub.user.exception.UserNotFoundException;
import com.mangahub.user.model.UserRecord;
import com.mangahub.user.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public UserRecord login(String username, String password) throws UserNotFoundException {
        try {
            username = username.trim();
            
            UserRecord user = userRepository.findUserByUsername(username);
            if (user == null) {
                throw new UserNotFoundException("User not found with username: " + username);
            }
            
            if (!user.verifyPassword(password)) {
                throw new UserNotFoundException("Password is incorrect for username: " + username);
            }
            
            return user;
        } catch (IOException e) {
            throw new RuntimeException("Error during login: " + e.getMessage(), e);
        }
    }
}
