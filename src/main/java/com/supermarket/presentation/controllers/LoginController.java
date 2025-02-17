package com.supermarket.presentation.controllers;

import com.supermarket.application.models.Branch;
import com.supermarket.application.models.User;
import com.supermarket.application.services.BranchService;
import com.supermarket.application.services.UserService;
import com.supermarket.presentation.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<Branch> branchComboBox;

    private App app;
    private Stage primaryStage;

    private final UserService userService = new UserService();
    private final BranchService branchService = new BranchService();

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    public void initialize() {
        try {
            List<Branch> branches = branchService.getAllBranches();
            branchComboBox.setItems(FXCollections.observableArrayList(branches));
        } catch (Exception e) {
            showError("Failed to load branches: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Branch selectedBranch = branchComboBox.getValue();

        if (selectedBranch == null) {
            showError("Please select a branch.");
            return;
        }

        try {
            User user = userService.getUserByUsername(username);
            if (user != null && user.getBranchId() == selectedBranch.getId()) {
                if (userService.authenticate(username, password)) {
                    app.setUserRole(user.getRole()); // Set the user's role
                    app.setBranchId(selectedBranch.getId()); // Set the selected branch ID
                    showSuccess("Login successful!");
                    navigateToDashboard(user.getRole());
                } else {
                    showError("Invalid username or password.");
                }
            } else {
                showError("Invalid username or password.");
            }
        } catch (SQLException e) {
            showError("Failed to authenticate: " + e.getMessage());
            logger.error("Authentication failed: {}", e.getMessage(), e);
        }
    }

    private void navigateToDashboard(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            app.showAdminDashboard();
        } else if ("cashier".equalsIgnoreCase(role)) {
            app.showCashierMenu();
        } else {
            showError("Unknown user role: " + role);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}