package com.supermarket.application.models;

public class Branch {
    private int id;
    private String name;
    private String location;

    // Constructor
    public Branch(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }

    // Override toString() to display branch name in ComboBox
    @Override
    public String toString() {
        return name;  // JavaFX will now display the branch name instead of the object reference
    }
}
