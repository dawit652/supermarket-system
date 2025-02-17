package com.supermarket.application.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final IntegerProperty loyaltyPoints = new SimpleIntegerProperty();

    public Customer(int id, String name, String phoneNumber, int loyaltyPoints) {
        setId(id);
        setName(name);
        setPhoneNumber(phoneNumber);
        setLoyaltyPoints(loyaltyPoints);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints.get();
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints.set(loyaltyPoints);
    }

    public IntegerProperty loyaltyPointsProperty() {
        return loyaltyPoints;
    }
}