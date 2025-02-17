package com.supermarket.presentation.controllers;

import com.supermarket.presentation.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavigationBarController {
    private static final Logger logger = LoggerFactory.getLogger(NavigationBarController.class);

    private App app;

    public void setApp(App app) {
        this.app = app; // Store the App instance for navigation
    }

    @FXML
    private void showSalesManagement() {
        if (app != null) {
            String userRole = app.getCurrentUserRole();
            if ("admin".equalsIgnoreCase(userRole) || "cashier".equalsIgnoreCase(userRole)) {
                app.showSalesManagement(); // Navigate to Sales Management
            } else {
                logger.warn("User does not have permission to access Sales Management.");
                showError("You do not have permission to access Sales Management.");
            }
        } else {
            logger.error("App reference is null. Navigation failed.");
            System.err.println("App reference is null. Navigation failed.");
        }
    }

    @FXML
    private void showProductManagement() {
        if (app != null) {
            String userRole = app.getCurrentUserRole();
            if ("admin".equalsIgnoreCase(userRole)) {
                app.showProductManagement(); // Navigate to Product Management
            } else {
                logger.warn("User does not have permission to access Product Management.");
                showError("You do not have permission to access Product Management.");
            }
        } else {
            logger.error("App reference is null. Navigation failed.");
            System.err.println("App reference is null. Navigation failed.");
        }
    }

// Add similar methods for Customer Management, Branch Management, etc.

    @FXML
    private void showCustomerManagement() {
        if (app != null) {
            String userRole = app.getCurrentUserRole();
            if ("admin".equalsIgnoreCase(userRole)) {
                app.showCustomerManagement(); // Navigate to Customer Management
            } else {
                logger.warn("User does not have permission to access Customer Management.");
                showError("You do not have permission to access Customer Management.");
            }
        } else {
            logger.error("App reference is null. Navigation failed.");
            System.err.println("App reference is null. Navigation failed.");
        }
    }

    @FXML
    private void showBranchManagement() {
        if (app != null) {
            String userRole = app.getCurrentUserRole();
            if ("admin".equalsIgnoreCase(userRole)) {
                app.showBranchManagement(); // Navigate to Branch Management
            } else {
                logger.warn("User does not have permission to access Branch Management.");
                showError("You do not have permission to access Branch Management.");
            }
        } else {
            logger.error("App reference is null. Navigation failed.");
            System.err.println("App reference is null. Navigation failed.");
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
}