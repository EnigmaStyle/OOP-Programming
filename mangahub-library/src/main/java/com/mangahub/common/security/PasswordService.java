package com.mangahub.common.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servizio centralizzato per la gestione delle password con BCrypt
 */
public class PasswordService {
    
    // Numero di round per il salt (più alto = più sicuro ma più lento)
    private static final int SALT_ROUNDS = 12;
    
    /**
     * Genera hash della password usando BCrypt
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(SALT_ROUNDS));
    }
    
    /**
     * Verifica se la password corrisponde all'hash
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Log silenzioso per evitare spam nei test
            return false;
        }
    }
    
    /**
     * Controlla se una stringa è un hash BCrypt valido
     */
    public static boolean isBCryptHash(String password) {
        return password != null && 
               (password.startsWith("$2a$") || 
                password.startsWith("$2b$") || 
                password.startsWith("$2y$"));
    }
}
