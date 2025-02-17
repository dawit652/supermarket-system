package com.supermarket.presentation.controllers;

import com.supermarket.application.models.Customer;
import com.supermarket.application.services.CustomerService;
import com.supermarket.presentation.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.List;

public class CustomerController {
    private App app;
    private final CustomerService customerService = new CustomerService();

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;
    @FXML
    private TableColumn<Customer, Integer> loyaltyPointsColumn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField loyaltyPointsField;

    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public void setApp(App app) {
        this.app = app; // Store the App instance
        loadCustomers(); // Load customers when the controller is initialized
    }

    @FXML
    private void initialize() {
        // Set up table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        loyaltyPointsColumn.setCellValueFactory(cellData -> cellData.getValue().loyaltyPointsProperty().asObject());

        // Add listener for customer selection
        customerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Populate input fields with selected customer's data
                nameField.setText(newValue.getName());
                phoneNumberField.setText(newValue.getPhoneNumber());
                loyaltyPointsField.setText(String.valueOf(newValue.getLoyaltyPoints()));
            }
        });
    }

    /**
     * Loads all customers into the table.
     */
    private void loadCustomers() {
        try {
            customers.clear();
            List<Customer> allCustomers = customerService.getAllCustomers();
            customers.addAll(allCustomers);
            customerTable.setItems(customers);
        } catch (SQLException e) {
            showError("Failed to load customers: " + e.getMessage());
        }
    }

    /**
     * Adds a new customer.
     */
    @FXML
    private void addCustomer() {
        try {
            String name = nameField.getText();
            String phoneNumber = phoneNumberField.getText();
            int loyaltyPoints = Integer.parseInt(loyaltyPointsField.getText());

            if (name.isEmpty() || phoneNumber.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            Customer newCustomer = new Customer(0, name, phoneNumber, loyaltyPoints);
            customerService.addCustomer(newCustomer);
            loadCustomers(); // Refresh the table

            clearFields(); // Clear input fields
            showSuccess("Customer added successfully!");
        } catch (NumberFormatException e) {
            showError("Invalid loyalty points. Please enter a valid number.");
        } catch (SQLException e) {
            showError("Failed to add customer: " + e.getMessage());
        }
    }

    /**
     * Updates an existing customer.
     */
    @FXML
    private void updateCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showError("Please select a customer to update.");
            return;
        }

        try {
            String name = nameField.getText();
            String phoneNumber = phoneNumberField.getText();
            int loyaltyPoints = Integer.parseInt(loyaltyPointsField.getText());

            if (name.isEmpty() || phoneNumber.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            selectedCustomer.setName(name);
            selectedCustomer.setPhoneNumber(phoneNumber);
            selectedCustomer.setLoyaltyPoints(loyaltyPoints);

            customerService.updateCustomer(selectedCustomer);
            loadCustomers(); // Refresh the table

            clearFields(); // Clear input fields
            showSuccess("Customer updated successfully!");
        } catch (NumberFormatException e) {
            showError("Invalid loyalty points. Please enter a valid number.");
        } catch (SQLException e) {
            showError("Failed to update customer: " + e.getMessage());
        }
    }

    /**
     * Deletes a selected customer.
     */
    @FXML
    private void deleteCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showError("Please select a customer to delete.");
            return;
        }

        try {
            customerService.deleteCustomer(selectedCustomer.getId());
            loadCustomers(); // Refresh the table
            clearFields(); // Clear input fields
            showSuccess("Customer deleted successfully!");
        } catch (SQLException e) {
            showError("Failed to delete customer: " + e.getMessage());
        }
    }

    /**
     * Navigates back to the dashboard.
     */
    @FXML
    private void navigateBack() {
        if (app != null) {
            app.showDashboard(); // Navigate back to the dashboard
        } else {
            System.err.println("App reference is null. Navigation failed.");
        }
    }

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        nameField.clear();
        phoneNumberField.clear();
        loyaltyPointsField.clear();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a success message to the user.
     *
     * @param message The success message to display.
     */
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}