package models;

import enums.UserRole;

public abstract class User extends Person {

    protected String password;
    protected UserRole role;
    protected boolean enabled;

    public User(String name, String username, String password, UserRole role) {
        super(name,username);
        this.password = password;
        this.role = role;
        this.enabled = true;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}