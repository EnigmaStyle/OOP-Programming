package com.mangahub.user.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mangahub.common.security.PasswordService;
import com.mangahub.user.model.UserRecord;
import com.mangahub.user.model.UserRole;

public class UserRepository {
    private static final String USERS_FILE = "users.json";
    private final ObjectMapper objectMapper;

    public UserRepository() {
        this.objectMapper = new ObjectMapper();
    }

    public List<UserRecord> loadUsers() throws IOException {
        File file = new File(USERS_FILE);
        
        if (!file.exists()) {
            createInitialUsersFile();
        }

        ObjectNode rootNode = (ObjectNode) objectMapper.readTree(file);
        ArrayNode usersArray = (ArrayNode) rootNode.get("users");
        
        List<UserRecord> users = new ArrayList<>();
        boolean needsMigration = false;
        
        if (usersArray != null) {
            for (int i = 0; i < usersArray.size(); i++) {
                ObjectNode userNode = (ObjectNode) usersArray.get(i);
                String username = userNode.get("username").asText();
                String password = userNode.get("password").asText();
                String roleStr = userNode.get("role").asText();
                
                UserRole role = "ADMIN".equals(roleStr) ? UserRole.ADMIN : UserRole.READER;
                
                // Controlla se la password è in chiaro e deve essere hashata
                if (!PasswordService.isBCryptHash(password)) {
                    needsMigration = true;
                    // Crea utente con password hashata
                    UserRecord user = new UserRecord(username, password, role);
                    users.add(user);
                } else {
                    // Password già hashata
                    UserRecord user = new UserRecord(username, password, role, true);
                    users.add(user);
                }
            }
        }
        
        // Se ci sono password in chiaro, salva il file con password hashate
        if (needsMigration) {
            System.out.println("🔄 Migrazione password in corso...");
            saveUsers(users);
            System.out.println("✅ Password migrate e hashate con successo!");
        }
        
        return users;
    }

    private void createInitialUsersFile() throws IOException {
        List<UserRecord> defaultUsers = new ArrayList<>();
        
        // Crea utenti predefiniti con password hashate
        defaultUsers.add(new UserRecord("User", "user123", UserRole.READER));
        defaultUsers.add(new UserRecord("Admin", "admin123", UserRole.ADMIN));
        
        saveUsers(defaultUsers);
    }

    public void saveUsers(List<UserRecord> users) throws IOException {
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode usersArray = objectMapper.createArrayNode();
        
        for (UserRecord user : users) {
            ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("username", user.getUsername());
            userNode.put("password", user.getPassword()); // Sempre hashata
            userNode.put("role", user.getRole().getRole());
            usersArray.add(userNode);
        }
        
        rootNode.set("users", usersArray);
        
        File file = new File(USERS_FILE);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);
    }

    public boolean userExists(String username) throws IOException {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        List<UserRecord> users = loadUsers();
        return users.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username.trim()));
    }

    public UserRecord findUserByUsername(String username) throws IOException {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        
        List<UserRecord> users = loadUsers();
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst()
                .orElse(null);
    }

    public void addUser(UserRecord user) throws IOException {
        List<UserRecord> users = loadUsers();
        
        // Verifica che non esista già
        boolean exists = users.stream()
                .anyMatch(existingUser -> 
                    existingUser.getUsername().equalsIgnoreCase(user.getUsername()));
        
        if (!exists) {
            users.add(user);
            saveUsers(users);
        }
    }
}
