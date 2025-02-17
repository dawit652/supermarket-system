package com.supermarket.data.dao;

import com.supermarket.application.models.Customer;
import com.supermarket.data.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO {
    /**
     * Adds a new customer to the database.
     *
     * @param customer The customer to add.
     * @throws SQLException If a database error occurs.
     */
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (name, phone_number, loyalty_points) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhoneNumber());
            stmt.setInt(3, customer.getLoyaltyPoints());
            stmt.executeUpdate();
        }
    }

    /**
     * Updates an existing customer in the database.
     *
     * @param customer The customer to update.
     * @throws SQLException If a database error occurs.
     */
    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name = ?, phone_number = ?, loyalty_points = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhoneNumber());
            stmt.setInt(3, customer.getLoyaltyPoints());
            stmt.setInt(4, customer.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a customer from the database by ID.
     *
     * @param customerId The ID of the customer to delete.
     * @throws SQLException If a database error occurs.
     */
    public void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        }
    }

    /**
     * Fetches all customers from the database.
     *
     * @return A list of all customers.
     * @throws SQLException If a database error occurs.
     */
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id, name, phone_number, loyalty_points FROM customer";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                int loyaltyPoints = rs.getInt("loyalty_points");
                customers.add(new Customer(id, name, phoneNumber, loyaltyPoints));
            }
        }
        return customers;
    }

    /**
     * Fetches a customer by ID.
     *
     * @param customerId The ID of the customer to fetch.
     * @return An Optional containing the customer if found, or empty otherwise.
     * @throws SQLException If a database error occurs.
     */
    public Optional<Customer> getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT id, name, phone_number, loyalty_points FROM customer WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String phoneNumber = rs.getString("phone_number");
                    int loyaltyPoints = rs.getInt("loyalty_points");
                    return Optional.of(new Customer(customerId, name, phoneNumber, loyaltyPoints));
                }
            }
        }
        return Optional.empty();
    }
}