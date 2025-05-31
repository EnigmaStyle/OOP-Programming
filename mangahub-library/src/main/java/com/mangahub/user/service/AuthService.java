package com.mangahub.user.service;

import java.io.IOException;

import com.mangahub.user.exception.UserAlreadyExistsException;
import com.mangahub.user.exception.UserNotFoundException;
import com.mangahub.user.model.UserRecord;
import com.mangahub.user.model.UserRole;
import com.mangahub.user.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;
    private static final boolean DEBUG_MODE = true; // Imposta a false in produzione

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    /**
     * Registra un nuovo utente con password hashata
     */
    public void register(String username, String password, UserRole role) 
            throws UserAlreadyExistsException {
        try {
            // Validazione input
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username non può essere vuoto");
            }
            
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password non può essere vuota");
            }
            
            if (password.length() < 6) {
                throw new IllegalArgumentException("Password deve essere di almeno 6 caratteri");
            }
            
            username = username.trim();
            
            // Verifica se l'utente esiste già
            if (userRepository.userExists(username)) {
                throw new UserAlreadyExistsException("Utente già esistente: " + username);
            }
            
            // Crea nuovo utente (password viene hashata automaticamente)
            UserRecord newUser = new UserRecord(username, password, role);
            userRepository.addUser(newUser);
            
        } catch (IOException e) {
            throw new RuntimeException("Errore durante la registrazione: " + e.getMessage(), e);
        }
    }

    /**
     * Effettua il login verificando username e password (con debug mode automatico)
     */
    public UserRecord login(String username, String password) throws UserNotFoundException {
        return login(username, password, DEBUG_MODE);
    }

    /**
     * Effettua il login verificando username e password
     * @param username Nome utente
     * @param password Password in chiaro
     * @param debugMode Se true, consente l'accesso con password in chiaro
     */
    public UserRecord login(String username, String password, boolean debugMode) throws UserNotFoundException {
        try {
            // Validazione input
            if (username == null || username.trim().isEmpty()) {
                throw new UserNotFoundException("Username non può essere vuoto");
            }
            
            if (password == null || password.trim().isEmpty()) {
                throw new UserNotFoundException("Password non può essere vuota");
            }
            
            username = username.trim();
            
            // Cerca l'utente
            UserRecord user = userRepository.findUserByUsername(username);
            if (user == null) {
                throw new UserNotFoundException("Utente non trovato: " + username);
            }
            
            // Verifica la password
            if (!user.verifyPassword(password, debugMode)) {
                throw new UserNotFoundException("Credenziali non valide per: " + username);
            }
            
            return user;
            
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il login: " + e.getMessage(), e);
        }
    }
}
