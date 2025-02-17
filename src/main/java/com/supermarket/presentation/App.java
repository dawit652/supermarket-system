package com.supermarket.presentation;

import com.supermarket.application.services.BranchService;
import com.supermarket.presentation.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static com.supermarket.utils.AlertUtil.showError;

public class App extends Application {
    private Stage primaryStage;
    private String userRole; // Store the user's role
    private int branchId; // Store the selected branch ID

    public void setUserRole(String role) {
        this.userRole = role;
    }

    public String getUserRole() {
        return userRole;
    }
    public String getCurrentUserRole() {
        return userRole; // Return the stored user role
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getBranchId() {
        return branchId;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Supermarket Management System");

        // Add a global window listener for the "X" button
        primaryStage.setOnCloseRequest(this::handleWindowClose);

        showLoginScreen();
    }

    /**
     * Handles the "X" button press on the window.
     *
     * @param event The window close event.
     */
    private void handleWindowClose(WindowEvent event) {
        event.consume(); // Prevent the default behavior (closing the application)

        try {
            if (isUserLoggedIn()) {
                String userRole = getCurrentUserRole();
                if ("admin".equalsIgnoreCase(userRole)) {
                    showDashboard(); // Redirect admin users to the dashboard
                } else if ("cashier".equalsIgnoreCase(userRole)) {
                    showCashierMenu(); // Redirect cashier users to the cashier menu
                } else {
                    showError("Unknown user role. Redirecting to login screen.");
                    showLoginScreen(); // Fallback to login screen
                }
            } else {
                showLoginScreen(); // If no user is logged in, go back to the login screen
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("An error occurred while redirecting. Redirecting to login screen.");
            showLoginScreen(); // Fallback to login screen
        }
    }
    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    private boolean isUserLoggedIn() {
        // Implement logic to check if a user is logged in
        // For example, check if the current user session exists
        return getCurrentUserRole() != null;
    }

    /**
     * Retrieves the current user's role.
     *
     * @return The user's role (e.g., "admin", "cashier"), or null if no user is logged in.
     */
    public void showSalesManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sales.fxml"));
            Parent root = loader.load();
            SalesController controller = loader.getController();
            controller.setApp(this); // Pass the App instance
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - Sales Management");
        } catch (IOException e) {
            showError("Failed to load Sales Management screen: " + e.getMessage());
        }
    }

    public void showProductManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inventory.fxml"));
            Parent root = loader.load();
            InventoryController controller = loader.getController();
            controller.setApp(this); // Pass the App instance
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - Product Management");
        } catch (IOException e) {
            showError("Failed to load Product Management screen: " + e.getMessage());
        }
    }
    public void showUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user_management.fxml"));
            Parent root = loader.load();
            UserController controller = loader.getController();
            controller.setApp(this); // Pass the App instance to the controller
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - User Management");
        } catch (IOException e) {
            showError("Failed to load User Management screen: " + e.getMessage());
        }
    }

    public void showReports() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reports.fxml"));
            Parent root = loader.load();
            ReportController controller = loader.getController();
            controller.setApp(this); // Pass the App instance
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - Reports");
        } catch (IOException e) {
            showError("Failed to load Reports screen: " + e.getMessage());
        }
    }
    public void showBranchManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/branch.fxml"));
            Parent root = loader.load();
            BranchController controller = loader.getController();
            controller.setApp(this); // Pass the App instance to the controller
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - Branch Management");
        } catch (IOException e) {
            showError("Failed to load Branch Management screen: " + e.getMessage());
        }
    }

// Add similar methods for Customer Management, Branch Management, etc.

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setApp(this);
            Scene scene = new Scene(root, 400, 300);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setApp(this);
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the admin dashboard.
     */
    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setApp(this); // Pass the App instance

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCashierMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cashier_menu.fxml"));
            Parent root = loader.load();
            CashierMenuController controller = loader.getController();
            controller.setApp(this); // Pass the App instance
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - Cashier Menu");
        } catch (IOException e) {
            showError("Failed to load Cashier Menu: " + e.getMessage());
        }
    }

    public void showCustomerManagement() {
        if (!"admin".equalsIgnoreCase(userRole)) {
            showError("You do not have permission to access Customer Management.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customer.fxml"));
            Parent root = loader.load();
            CustomerController controller = loader.getController();
            controller.setApp(this);
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Supermarket Management System - Customer Management");
        } catch (IOException e) {
            showError("Failed to load Customer Management screen: " + e.getMessage());
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


    // Add other navigation methods here...

    public static void launchApp(String[] args) {
        launch(args);
    }
}