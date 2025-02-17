package com.supermarket.application.models;

public class Product {
    private int id;
    private String name;
    private String barcode;
    private double price;
    private int stock;
    private int branchId;

    // Constructor with ID (for fetching from the database)
    public Product(int id, String name, String barcode, double price, int stock, int branchId) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.stock = stock;
        this.branchId = branchId;
    }

    // Constructor without ID (for creating new products)
    public Product(String name, String barcode, double price, int stock, int branchId) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.stock = stock;
        this.branchId = branchId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBarcode() { return barcode; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public int getBranchId() { return branchId; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setBranchId(int branchId) { this.branchId = branchId; }
}