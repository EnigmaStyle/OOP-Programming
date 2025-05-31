package com.mangahub.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility per generare hash delle password per testing
 */
public class PasswordHashGenerator {
    public static void main(String[] args) {
        // Genera hash per le password di test
        String userPassword = "user123";
        String adminPassword = "admin123";
        
        String userHash = BCrypt.hashpw(userPassword, BCrypt.gensalt());
        String adminHash = BCrypt.hashpw(adminPassword, BCrypt.gensalt());
        
        System.out.println("=== PASSWORD HASHES ===");
        System.out.println("User password (" + userPassword + "): " + userHash);
        System.out.println("Admin password (" + adminPassword + "): " + adminHash);
        
        // Verifica che gli hash funzionino
        System.out.println("\n=== VERIFICATION ===");
        System.out.println("User hash verification: " + BCrypt.checkpw(userPassword, userHash));
        System.out.println("Admin hash verification: " + BCrypt.checkpw(adminPassword, adminHash));
    }
}
