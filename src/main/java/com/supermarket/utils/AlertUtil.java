package com.supermarket.utils;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}