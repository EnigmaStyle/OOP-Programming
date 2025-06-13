package com.mangahub.user.model;

public class UserRole {
    public static final UserRole ADMIN = new UserRole("ADMIN");
    public static final UserRole READER = new UserRole("READER");

    private final String role;

    public UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserRole userRole = (UserRole) obj;
        return role.equals(userRole.role);
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }

    @Override
    public String toString() {
        return role;
    }
}
