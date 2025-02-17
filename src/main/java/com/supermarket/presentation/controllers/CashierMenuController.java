package com.supermarket.presentation.controllers;

import com.supermarket.presentation.App;
import com.supermarket.utils.LocalizationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CashierMenuController {
    private static final Logger logger = LoggerFactory.getLogger(CashierMenuController.class);

    private App app; // Reference to the main application

    @FXML
    private Label welcomeLabel; // Welcome message label
    @FXML
    private Button salesButton; // Sales Management button
    @FXML
    private Button inventoryButton; // Product Management button
    @FXML
    private Button logoutButton; // Logout button
    @FXML
    private Button toggleLanguageButton; // Toggle Language button

    public void setApp(App app) {
        this.app = app; // Store the App instance
        initializeUI(); // Initialize UI components with localized strings
        logger.info("App instance set successfully in CashierMenuController.");
    }

    @FXML
    private void showSalesManagement() {
        if (app != null) {
            app.showSalesManagement(); // Navigate to the Sales Management screen
        } else {
            logger.error("App reference is null. Navigation failed.");
            showError(LocalizationUtil.getLocalizedString("navigation_failed", "Sales Management"));
        }
    }

    @FXML
    private void showProductManagement() {
        if (app != null) {
            String userRole = app.getCurrentUserRole();
            if ("admin".equalsIgnoreCase(userRole)) {
                app.showProductManagement(); // Only admins can access Product Management
            } else {
                logger.warn("User does not have permission to access Product Management.");
                showError(LocalizationUtil.getLocalizedString("permission_denied", "Product Management"));
            }
        } else {
            logger.error("App reference is null. Navigation failed.");
            showError(LocalizationUtil.getLocalizedString("navigation_failed", "Product Management"));
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(LocalizationUtil.getLocalizedString("error"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void logout() {
        if (app != null) {
            app.showLoginScreen(); // Navigate back to the login screen
        } else {
            logger.error("App reference is null. Logout failed.");
            showError(LocalizationUtil.getLocalizedString("navigation_failed", "Logout"));
        }
    }

    @FXML
    private void toggleLanguage() {
        try {
            String currentLanguage = LocalizationUtil.getCurrentLanguage();
            if ("en".equalsIgnoreCase(currentLanguage)) {
                LocalizationUtil.setLanguage("am"); // Switch to Amharic
            } else {
                LocalizationUtil.setLanguage("en"); // Switch to English
            }
            reloadUIWithNewLanguage();
            showSuccess(LocalizationUtil.getLocalizedString("language_toggled_successfully"));
        } catch (Exception e) {
            showError(LocalizationUtil.getLocalizedString("failed_to_load", "Language", e.getMessage()));
        }
    }

    /**
     * Initializes the UI components with localized strings.
     */
    private void initializeUI() {
        if (welcomeLabel != null) {
            welcomeLabel.setText(LocalizationUtil.getLocalizedString("welcome_cashier", "Cashier")); // Pass "Cashier" as an argument
            welcomeLabel.getStyleClass().add("label"); // Add CSS class
        }

        if (salesButton != null) {
            salesButton.setText(LocalizationUtil.getLocalizedString("sales_management")); // Set button text
            salesButton.getStyleClass().add("button primary-button"); // Add CSS class
        }

        if (inventoryButton != null) {
            inventoryButton.setText(LocalizationUtil.getLocalizedString("product_management")); // Set button text
            inventoryButton.getStyleClass().add("button secondary-button"); // Add CSS class
        }

        if (logoutButton != null) {
            logoutButton.setText(LocalizationUtil.getLocalizedString("logout")); // Set button text
            logoutButton.getStyleClass().add("button danger-button"); // Add CSS class
        }

        if (toggleLanguageButton != null) {
            toggleLanguageButton.setText(LocalizationUtil.getLocalizedString("toggle_language")); // Set button text
            toggleLanguageButton.getStyleClass().add("toggle-language-button"); // Add CSS class
        }
    }

    /**
     * Reloads the UI components with the updated language.
     */
    private void reloadUIWithNewLanguage() {
        if (welcomeLabel != null) {
            welcomeLabel.setText(LocalizationUtil.getLocalizedString("welcome_cashier", "Cashier")); // Pass "Cashier" as an argument
        }

        if (salesButton != null) {
            salesButton.setText(LocalizationUtil.getLocalizedString("sales_management"));
        }

        if (inventoryButton != null) {
            inventoryButton.setText(LocalizationUtil.getLocalizedString("product_management"));
        }

        if (logoutButton != null) {
            logoutButton.setText(LocalizationUtil.getLocalizedString("logout"));
        }

        if (toggleLanguageButton != null) {
            toggleLanguageButton.setText(LocalizationUtil.getLocalizedString("toggle_language"));
        }
    }



    /**
     * Displays a success message to the user.
     *
     * @param message The success message to display.
     */
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LocalizationUtil.getLocalizedString("success"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}