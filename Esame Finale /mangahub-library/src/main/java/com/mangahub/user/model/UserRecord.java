package com.mangahub.user.model;

import org.mindrot.jbcrypt.BCrypt;

public class UserRecord {
    private final String username;
    private final String password; // Questa dovrebbe essere l'hash della password
    private final UserRole role;

    public UserRecord(String username, String password, UserRole role) {
        this.username = username;
        this.password = hashPassword(password); // Hash della password al momento della creazione
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password; // Restituisce l'hash della password
    }

    public UserRole getRole() {
        return role;
    }

    // Metodo per ottenere l'hash della password
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt()); // Restituisce l'hash della password
    }

    // Metodo per verificare se la password fornita corrisponde all'hash
    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password); // Verifica la password
    }
}
