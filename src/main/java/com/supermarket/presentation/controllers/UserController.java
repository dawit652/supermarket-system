package com.supermarket.presentation.controllers;

import com.supermarket.application.models.Branch;
import com.supermarket.application.models.User;
import com.supermarket.application.services.BranchService;
import com.supermarket.application.services.UserService;
import com.supermarket.presentation.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private ComboBox<Branch> branchComboBox; // Ensure this matches the type Branch

    private final UserService userService = new UserService();
    private final BranchService branchService = new BranchService();

    private App app;

    public void setApp(App app) {
        this.app = app; // Store the App instance for navigation
    }

    @FXML
    private void navigateBack() {
        if (app != null) {
            app.showDashboard(); // Navigate back to the dashboard
        } else {
            System.err.println("App reference is null. Navigation failed.");
        }
    }

    /**
     * Initializes the controller by loading branches into the ComboBox.
     */
    @FXML
    public void initialize() {
        try {
            // Fetch branches from the service layer
            List<Branch> branches = branchService.getAllBranches();

            // Convert List<Branch> to ObservableList<Branch>
            ObservableList<Branch> observableBranches = FXCollections.observableArrayList(branches);

            // Set the ObservableList to the ComboBox
            branchComboBox.setItems(observableBranches);
        } catch (SQLException e) {
            showError("Failed to load branches: " + e.getMessage());
        }
    }

    /**
     * Adds a new user with a plain-text password.
     */
    @FXML
    private void addUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();
        Branch selectedBranch = branchComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null || selectedBranch == null) {
            showError("Please fill in all fields.");
            return;
        }

        try {
            // Create a new User object and pass it to the service layer
            userService.addUser(new User(username, password, role, selectedBranch.getId()));
            showSuccess("User added successfully!");
            clearFields();
        } catch (SQLException e) {
            showError("Failed to add user: " + e.getMessage());
        }
    }

    /**
     * Updates an existing user with a plain-text password.
     */
    @FXML
    private void updateUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();
        Branch selectedBranch = branchComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null || selectedBranch == null) {
            showError("Please fill in all fields.");
            return;
        }

        try {
            // Update the user's details and pass it to the service layer
            userService.updateUser(new User(0, username, password, role, selectedBranch.getId()));
            showSuccess("User updated successfully!");
            clearFields();
        } catch (SQLException e) {
            showError("Failed to update user: " + e.getMessage());
        }
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

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        roleComboBox.setValue(null);
        branchComboBox.setValue(null);
    }
}