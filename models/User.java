package models;

import enums.UserRole;

public abstract class User extends Person {

    protected String password;
    protected UserRole role;

    public User(String name, String username, String password, UserRole role) {
        super(name,username);
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}