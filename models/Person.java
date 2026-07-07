package models;

public abstract class Person {

    protected String name;
    protected String username;

    public Person(String name, String username) {
        setName(name);
        setUsername(username);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}