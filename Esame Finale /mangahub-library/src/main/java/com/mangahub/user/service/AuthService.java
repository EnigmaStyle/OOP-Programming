package com.mangahub.user.service;

import java.util.HashMap;
import java.util.Map;

import com.mangahub.user.exception.UserAlreadyExistsException;
import com.mangahub.user.exception.UserNotFoundException;
import com.mangahub.user.model.UserRecord;
import com.mangahub.user.model.UserRole;

public class AuthService {
    private Map<String, UserRecord> users = new HashMap<>();

    public void register(String username, String password, UserRole role) throws UserAlreadyExistsException {
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException("User  already exists: " + username);
        }
        users.put(username, new UserRecord(username, password, role));
    }

    public UserRecord login(String username, String password) throws UserNotFoundException {
        UserRecord user = users.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new UserNotFoundException("User  not found: " + username);
        }
        return user;
    }
}
