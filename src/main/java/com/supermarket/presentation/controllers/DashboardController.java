package com.supermarket.presentation.controllers;

import com.supermarket.presentation.App;
import com.supermarket.utils.LocalizationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private App app;

    @FXML
    private Label titleLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private Button userManagementButton;
    @FXML
    private Button branchManagementButton;
    @FXML
    private Button customerManagementButton;
    @FXML
    private Button salesManagementButton;
    @FXML
    private Button productManagementButton;
    @FXML
    private Button reportsButton;

    public void setApp(App app) {
        this.app = app;
        reloadUIWithNewLanguage(); // Reload UI components with the current language
        logger.info("App instance set successfully in DashboardController.");
    }

    @FXML
    private void showUserManagement() {
        if (app != null) {
            String userRole = app.getCurrentUserRole();
            if ("admin".equalsIgnoreCase(userRole)) {
                app.showUserManagement(); // Navigate to User Management
            } else {
                logger.warn("User does not have permission to access User Management.");
                showError("You do not have permission to access User Management.");
            }
        } else {
            logger.error("App reference is null. Navigation failed.");
            System.err.println("App reference is null. Navigation failed.");
        }
    }

    @FXML
    private void showBranchManagement() {
        if (app != null) {
            app.showBranchManagement();
        } else {
            logger.error("App reference is null. Navigation failed.");
            showError("Navigation failed. Please contact the administrator.");
        }
    }

    @FXML
    private void showCustomerManagement() {
        if (app != null) {
            app.showCustomerManagement();
        } else {
            logger.error("App reference is null. Navigation failed.");
            showError("Navigation failed. Please contact the administrator.");
        }
    }

    @FXML
    private void showSalesManagement() {
        if (app != null) {
            app.showSalesManagement();
        } else {
            logger.error("App reference is null. Navigation failed.");
            showError("Navigation failed. Please contact the administrator.");
        }
    }

    @FXML
    private void showProductManagement() {
        if (app != null) {
            app.showProductManagement();
        } else {
            logger.error("App reference is null. Navigation failed.");
            showError("Navigation failed. Please contact the administrator.");
        }
    }

    @FXML
    private void showReports() {
        if (app != null) {
            app.showReports();
        } else {
            logger.error("App reference is null. Navigation failed.");
            showError("Navigation failed. Please contact the administrator.");
        }
    }

    @FXML
    private void logout() {
        if (app != null) {
            app.showLoginScreen(); // Navigate back to the login screen
        } else {
            logger.error("App reference is null. Logout failed.");
            showError("Logout failed. Please contact the administrator.");
        }
    }

    /**
     * Reloads the UI components with the updated language.
     */
    private void reloadUIWithNewLanguage() {
        if (titleLabel != null) {
            titleLabel.setText(LocalizationUtil.getLocalizedString("title_dashboard"));
        }

        if (welcomeLabel != null) {
            welcomeLabel.setText(LocalizationUtil.getLocalizedString("welcome_admin"));
        }

        if (logoutButton != null) {
            logoutButton.setText(LocalizationUtil.getLocalizedString("logout"));
        }

        if (userManagementButton != null) {
            userManagementButton.setText(LocalizationUtil.getLocalizedString("user_management"));
        }

        if (branchManagementButton != null) {
            branchManagementButton.setText(LocalizationUtil.getLocalizedString("branch_management"));
        }

        if (customerManagementButton != null) {
            customerManagementButton.setText(LocalizationUtil.getLocalizedString("customer_management"));
        }

        if (salesManagementButton != null) {
            salesManagementButton.setText(LocalizationUtil.getLocalizedString("sales_management"));
        }

        if (productManagementButton != null) {
            productManagementButton.setText(LocalizationUtil.getLocalizedString("product_management"));
        }

        if (reportsButton != null) {
            reportsButton.setText(LocalizationUtil.getLocalizedString("reports"));
        }
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(LocalizationUtil.getLocalizedString("error"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}