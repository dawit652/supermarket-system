package com.supermarket.application.models;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private int branchId; // New field for branch association

    // Constructor with ID (for fetching from the database)
    public User(int id, String username, String password, String role, int branchId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.branchId = branchId;
    }

    // Constructor without ID (for creating new users)
    public User(String username, String password, String role, int branchId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.branchId = branchId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}