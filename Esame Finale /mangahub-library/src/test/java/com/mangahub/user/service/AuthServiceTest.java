package com.mangahub.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mangahub.user.exception.UserAlreadyExistsException;
import com.mangahub.user.exception.UserNotFoundException;
import com.mangahub.user.model.UserRecord;
import com.mangahub.user.model.UserRole;

public class AuthServiceTest {
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthService(); // Inizializza il servizio di autenticazione
    }

    @Test
    public void testRegisterAndLogin() {
        // Registrazione di un nuovo utente
        String username = "testUser "; // Nome utente da registrare (senza spazi)
        String password = "testPassword"; // Password da registrare
        UserRole role = UserRole.READER; // Definisci il ruolo
        try {
            authService.register(username, password, role); // Register the user
        } catch (UserAlreadyExistsException e) {
            fail("UserAlreadyExistsException was thrown during registration: " + e.getMessage());
        }
        
        // Prova a fare il login
        try {
            UserRecord user = authService.login(username, password); // Perform login
            assertNotNull(user); // Verifica che l'utente non sia nullo
        } catch (UserNotFoundException e) {
            fail("UserNotFoundException was thrown during login: " + e.getMessage());
            return; // Exit the test if login fails
        }
        try {
            UserRecord user = authService.login(username, password); // Retrieve the user after successful login
            assertEquals(username, user.getUsername()); // Verifica che il nome utente sia corretto
        } catch (UserNotFoundException e) {
            fail("UserNotFoundException was thrown during login: " + e.getMessage());
        }
    }

    @Test
    public void testRegisterAndLoginWithInvalidCredentials() {
        // Registrazione di un nuovo utente
        String username = "testUser "; // Nome utente da registrare (senza spazi)
        String password = "testPassword"; // Password da registrare
        UserRole role = UserRole.READER; // Definisci il ruolo
        try {
            authService.register(username, password, role); // Registra l'utente
        } catch (UserAlreadyExistsException e) {
            fail("UserAlreadyExistsException was thrown during registration: " + e.getMessage());
        }
        
        // Prova a fare il login con credenziali errate
        assertThrows(UserNotFoundException.class, () -> authService.login(username, "wrongPassword")); // Effettua il login
    }

    @Test
    public void testRegisterAndLoginWithExistingUser () {
        // Registrazione di un nuovo utente
        String username = "testUser "; // Nome utente da registrare (senza spazi)
        String password = "testPassword"; // Password da registrare
        UserRole role = UserRole.READER; // Definisci il ruolo
        try {
            authService.register(username, password, role); // Registra l'utente
        } catch (UserAlreadyExistsException e) {
            fail("UserAlreadyExistsException was thrown during registration: " + e.getMessage());
        }
        
        // Prova a registrare lo stesso utente
        assertThrows(UserAlreadyExistsException.class, () -> authService.register(username, password, role)); // Registra l'utente
    }

    @Test
    public void testLoginWithNonExistentUser () {
        // Prova a fare il login con un utente non esistente
        assertThrows(UserNotFoundException.class, () -> authService.login("nonExistentUser ", "password")); // Effettua il login
    }
}
