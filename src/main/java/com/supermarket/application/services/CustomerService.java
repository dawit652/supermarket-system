package com.supermarket.application.services;

import com.supermarket.application.models.Customer;
import com.supermarket.data.dao.CustomerDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    /**
     * Adds a new customer.
     *
     * @param customer The customer to add.
     * @throws SQLException If a database error occurs.
     */
    public void addCustomer(Customer customer) throws SQLException {
        if (customer.getLoyaltyPoints() < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative.");
        }
        customerDAO.addCustomer(customer);
    }

    /**
     * Updates an existing customer.
     *
     * @param customer The customer to update.
     * @throws SQLException If a database error occurs.
     */
    public void updateCustomer(Customer customer) throws SQLException {
        if (customer.getLoyaltyPoints() < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative.");
        }
        customerDAO.updateCustomer(customer);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param customerId The ID of the customer to delete.
     * @throws SQLException If a database error occurs.
     */
    public void deleteCustomer(int customerId) throws SQLException {
        customerDAO.deleteCustomer(customerId);
    }

    /**
     * Fetches all customers.
     *
     * @return A list of all customers.
     * @throws SQLException If a database error occurs.
     */
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    /**
     * Fetches a customer by ID.
     *
     * @param customerId The ID of the customer to fetch.
     * @return The customer object if found, null otherwise.
     */
    public Customer getCustomerById(int customerId) {
        try {
            return customerDAO.getCustomerById(customerId).orElse(null); // Extract the value from Optional
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch customer by ID", e);
        }
    }
}