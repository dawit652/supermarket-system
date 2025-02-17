package com.supermarket.presentation.controllers;

import com.supermarket.application.services.ReportService;
import com.supermarket.presentation.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ReportController {
    private App app;
    private final ReportService reportService = new ReportService();

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void generateSalesReport() {
        try {
            reportService.generateSalesReport();
            showSuccess("Sales report generated successfully.");
        } catch (Exception e) {
            showError("Failed to generate sales report: " + e.getMessage());
        }
    }

    @FXML
    private void generateInventoryReport() {
        try {
            reportService.generateInventoryReport();
            showSuccess("Inventory report generated successfully.");
        } catch (Exception e) {
            showError("Failed to generate inventory report: " + e.getMessage());
        }
    }

    @FXML
    private void navigateBack() {
        if (app != null) {
            app.showDashboard();
        } else {
            showError("Navigation failed.");
        }
    }

    private void showError(String message) {
        // Display error using an alert dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        // Display success using an alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}