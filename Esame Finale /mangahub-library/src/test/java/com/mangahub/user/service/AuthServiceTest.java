package com.mangahub.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        authService = new AuthService();
    }

    @Test
    public void testLoginWithPredefinedUser() throws UserNotFoundException {
        // Test con password in chiaro (modalità debug attiva)
        UserRecord user = authService.login("User", "user123");
        
        assertNotNull(user);
        assertEquals("User", user.getUsername());
        assertEquals(UserRole.READER, user.getRole());
    }

    @Test
    public void testLoginWithPredefinedAdmin() throws UserNotFoundException {
        // Test con password in chiaro (modalità debug attiva)
        UserRecord admin = authService.login("Admin", "admin123");
        
        assertNotNull(admin);
        assertEquals("Admin", admin.getUsername());
        assertEquals(UserRole.ADMIN, admin.getRole());
    }

    @Test
    public void testRegisterAndLogin() throws UserAlreadyExistsException, UserNotFoundException {
        String username = "testUser" + System.currentTimeMillis();
        String password = "testPassword123";
        
        // Registra nuovo utente
        authService.register(username, password, UserRole.READER);
        
        // Effettua login con password hashata
        UserRecord user = authService.login(username, password);
        
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(UserRole.READER, user.getRole());
    }

    @Test
    public void testRegisterAndLoginWithInvalidCredentials() throws UserAlreadyExistsException {
        String username = "testUser" + System.currentTimeMillis();
        String password = "testPassword123";
        
        // Registra utente
        authService.register(username, password, UserRole.READER);
        
        // Tenta login con password sbagliata
        assertThrows(UserNotFoundException.class, () -> 
            authService.login(username, "passwordSbagliata"));
    }

    @Test
    public void testRegisterExistingUser() {
        // Tenta di registrare un utente già esistente
        assertThrows(UserAlreadyExistsException.class, () -> 
            authService.register("User", "nuovaPassword", UserRole.READER));
    }

    @Test
    public void testLoginWithNonExistentUser() {
        assertThrows(UserNotFoundException.class, () -> 
            authService.login("utenteInesistente", "password"));
    }

    @Test
    public void testLoginWithWrongPassword() {
        assertThrows(UserNotFoundException.class, () -> 
            authService.login("User", "passwordSbagliata"));
    }

    @Test
    public void testDebugModeLogin() throws UserNotFoundException {
        // Test esplicito della modalità debug
        UserRecord user = authService.login("User", "user123", true);
        assertNotNull(user);
        assertEquals("User", user.getUsername());
        
        UserRecord admin = authService.login("Admin", "admin123", true);
        assertNotNull(admin);
        assertEquals("Admin", admin.getUsername());
    }

    @Test
    public void testProductionModeLoginWithHashedPasswords() throws UserNotFoundException {
        // Dopo la migrazione, le password sono hashate quindi il login funziona
        // anche in modalità produzione se usiamo la password corretta
        
        // Test che il login funzioni con password corrette anche in modalità produzione
        UserRecord user = authService.login("User", "user123", false);
        assertNotNull(user);
        assertEquals("User", user.getUsername());
        
        // Test che password sbagliate falliscano in modalità produzione
        assertThrows(UserNotFoundException.class, () -> 
            authService.login("User", "passwordSbagliata", false));
    }

    @Test
    public void testPasswordMigration() throws UserNotFoundException {
        // Verifica che dopo la migrazione, le password siano hashate
        // ma il login funzioni ancora
        UserRecord user = authService.login("User", "user123");
        assertNotNull(user);
        
        // Verifica che la password sia stata hashata
        assertTrue(user.getPassword().startsWith("$2a$"));
    }
}
