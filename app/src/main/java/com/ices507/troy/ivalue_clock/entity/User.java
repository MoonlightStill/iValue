package com.ices507.troy.ivalue_clock.entity;

/**
 * Created by troy on 17-11-14.
 */

public class User {
    private String ID;
    private String name;
    private String description;

    public User(String ID, String name, String description) {
        this.ID = ID;
        this.name = name;
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
