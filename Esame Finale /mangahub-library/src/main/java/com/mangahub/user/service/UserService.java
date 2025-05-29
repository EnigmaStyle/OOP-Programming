package com.mangahub.user.service;

import java.util.ArrayList;
import java.util.List;

import com.mangahub.user.exception.UserNotFoundException;
import com.mangahub.user.model.UserRecord;
import com.mangahub.user.model.UserRole;

public class UserService {
    // Simulazione di un "database" di utenti
    private List<UserRecord> userDatabase;

    public UserService() {
        // Inizializza il "database" con alcuni utenti di esempio
        userDatabase = new ArrayList<>();
        userDatabase.add(new UserRecord("testUser", "testPassword", UserRole.READER));
        userDatabase.add(new UserRecord("adminUser", "adminPassword", UserRole.ADMIN));
    }

    public UserRecord login(String username, String password) throws UserNotFoundException {
        // Cerca l'utente nel "database"
        for (UserRecord user : userDatabase) {
            if (user.getUsername().trim().equals(username.trim())) {
                // Verifica la password usando verifyPassword
                if (user.verifyPassword(password)) {
                    return user; // Restituisce l'oggetto UserRecord se le credenziali sono corrette
                } else {
                    throw new UserNotFoundException("Password is incorrect for username: " + username);
                }
            }
        }
        // Se l'utente non esiste, lancia UserNotFoundException
        throw new UserNotFoundException("User  not found with username: " + username);
    }
}
