package com.mangahub.user.model;

import com.mangahub.common.security.PasswordService;

public class UserRecord {
    private final String username;
    private final String password; // Sempre hashata
    private final UserRole role;
    private final String originalPlainPassword; // Memorizza la password originale per debug

    /**
     * Costruttore per nuovi utenti - hash automatico della password
     */
    public UserRecord(String username, String plainPassword, UserRole role) {
        this.username = username;
        this.password = PasswordService.hashPassword(plainPassword);
        this.role = role;
        this.originalPlainPassword = plainPassword; // Memorizza per debug
    }

    /**
     * Costruttore per caricare utenti dal database/JSON
     * @param username nome utente
     * @param password password (può essere già hashata o in chiaro)
     * @param role ruolo utente
     * @param isAlreadyHashed true se la password è già hashata
     */
    public UserRecord(String username, String password, UserRole role, boolean isAlreadyHashed) {
        this.username = username;
        
        if (isAlreadyHashed || PasswordService.isBCryptHash(password)) {
            this.password = password; // Già hashata
            this.originalPlainPassword = null; // Non conosciamo la password originale
        } else {
            this.originalPlainPassword = password; // Memorizza la password originale
            this.password = PasswordService.hashPassword(password); // Hash ora
        }
        
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    /**
     * Verifica se la password fornita corrisponde a quella dell'utente
     * @param plainPassword La password in chiaro da verificare
     * @param debugMode Se true, permette l'accesso con password in chiaro predefinite
     */
    public boolean verifyPassword(String plainPassword, boolean debugMode) {
        if (plainPassword == null || this.password == null) {
            return false;
        }
        
        // Se in modalità debug, consenti l'accesso con password in chiaro predefinite
        if (debugMode) {
            // Controlla le password predefinite
            if ((this.username.equals("User") && plainPassword.equals("user123")) ||
                (this.username.equals("Admin") && plainPassword.equals("admin123"))) {
                return true;
            }
            
            // Se abbiamo memorizzato la password originale, controllala
            if (this.originalPlainPassword != null && this.originalPlainPassword.equals(plainPassword)) {
                return true;
            }
        }
        
        // Verifica normale con BCrypt
        if (PasswordService.isBCryptHash(this.password)) {
            return PasswordService.verifyPassword(plainPassword, this.password);
        } else {
            // Fallback per password in chiaro (non dovrebbe mai accadere)
            return this.password.equals(plainPassword);
        }
    }
    
    /**
     * Metodo di verifica semplificato per compatibilità
     */
    public boolean verifyPassword(String plainPassword) {
        return verifyPassword(plainPassword, false);
    }
    
    @Override
    public String toString() {
        return "UserRecord{" +
                "username='" + username + '\'' +
                ", role=" + role.getRole() +
                ", passwordHashed=" + PasswordService.isBCryptHash(password) +
                '}';
    }
}
