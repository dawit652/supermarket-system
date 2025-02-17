package com.supermarket.presentation.controllers;

import com.supermarket.presentation.App;
import javafx.fxml.FXML;

public class BranchController {
    private App app;

    public void setApp(App app) {
        this.app = app; // Store the App instance
    }

    // Add any navigation logic here if needed
    @FXML
    private void navigateBack() {
        if (app != null) {
            app.showDashboard(); // Example: Navigate back to the dashboard
        } else {
            System.err.println("App reference is null. Navigation failed.");
        }
    }
}