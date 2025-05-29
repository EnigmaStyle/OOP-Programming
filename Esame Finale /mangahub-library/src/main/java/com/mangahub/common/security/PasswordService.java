package com.mangahub.common.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Servizio per la gestione delle password.
 */
public class PasswordService {
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
