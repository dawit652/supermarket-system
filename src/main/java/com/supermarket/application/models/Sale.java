package com.supermarket.application.models;

import java.util.Date;
import java.util.List;

public class Sale {
    private int id;
    private Date saleDate;
    private List<com.supermarket.application.models.Product> products;
    private double totalPrice;
    private double discount;
    private int branchId;
    private int quantity;

    // Constructor
    public Sale(int id, Date saleDate, List<com.supermarket.application.models.Product> products, double totalPrice, double discount, int branchId, int quantity) {
        this.id = id;
        this.saleDate = saleDate;
        this.products = products;
        this.totalPrice = totalPrice;
        this.discount= discount;
        this.branchId= branchId;
        this.quantity=quantity;

    }

    // Getters and Setters
    // Getters and Setters
    public double getDiscount() { return discount; }
    public int getId() { return id; }
    public Date getSaleDate() { return saleDate; }
    public List<com.supermarket.application.models.Product> getProducts() { return products; }
    public double getTotalPrice() { return totalPrice; }
    public int getBranchId() { return branchId; }
    public int getQuantity(){ return quantity;}

    public void setId(int id) { this.id = id; }
    public void setDiscount(double discount) { this.discount = discount; }
    public void setSaleDate(Date saleDate) { this.saleDate = saleDate; }
    public void setProducts(List<com.supermarket.application.models.Product> products) { this.products = products; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setBranchId(int branchId) { this.branchId = branchId; }
    public void setQuantity(int quantity){this.quantity=quantity;}
}