package com.mangahub.user.model;

public class UserRole {
    public static final UserRole ADMIN = new UserRole("ADMIN");
    public static final UserRole READER = new UserRole("READER");

    private String role;

    public UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
