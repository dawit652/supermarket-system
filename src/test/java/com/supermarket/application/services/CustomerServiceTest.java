package com.supermarket.application.services;

import com.supermarket.application.models.Customer;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    private final CustomerService customerService = new CustomerService();

    @Test
    void testAddCustomer() {
        Customer customer = new Customer(0, "John Doe", "1234567890", 100);
        try {
            customerService.addCustomer(customer);
            assertNotNull(customer.getId()); // Ensure ID is set after insertion
        } catch (SQLException e) {
            fail("Failed to add customer: " + e.getMessage());
        }
    }

    @Test
    void testGetAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            assertNotNull(customers);
        } catch (SQLException e) {
            fail("Failed to fetch customers: " + e.getMessage());
        }
    }
}