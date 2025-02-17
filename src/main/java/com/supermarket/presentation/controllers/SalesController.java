package com.supermarket.presentation.controllers;

import com.supermarket.application.models.Branch;
import com.supermarket.application.services.BranchService;
import com.supermarket.application.services.ProductService;
import com.supermarket.application.services.SalesService;
import com.supermarket.application.models.Product;
import com.supermarket.application.models.Sale;
import com.supermarket.presentation.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SalesController {
    @FXML
    private ComboBox<Branch> branchComboBox;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> stockColumn;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField discountField;
    @FXML
    private TextField totalPriceField;

    private final BranchService branchService = new BranchService();
    private final ProductService productService = new ProductService();
    private final SalesService salesService = new SalesService();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<Product> cart = FXCollections.observableArrayList();
    private App app;

    @FXML
    public void initialize() {
        branchComboBox.getItems().add(new Branch(1, "Main Branch", "Location 1"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        try {
            List<Branch> branches = branchService.getAllBranches();
            branchComboBox.setItems(FXCollections.observableArrayList(branches));
            System.out.println("Branches to be set in ComboBox: " + branches.size());


            branchComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    loadProducts(newValue.getId());
                }
            });
        } catch (SQLException e) {
            showError("Failed to load branches: " + e.getMessage());
        }
    }

    private void loadProducts(int branchId) {
        try {
            products.clear();
            products.addAll(productService.getAllProducts(branchId));
            productTable.setItems(products);
        } catch (SQLException e) {
            showError("Failed to load products: " + e.getMessage());
        }
    }

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void addToCart() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            int quantity = 1;
            try {
                quantity = Integer.parseInt(quantityField.getText());
                if (quantity < 0) {
                    showError("Quantity must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                quantity = 1;
            }

            cart.add(selectedProduct);
            updateTotalPrice();

        } else {
            showError("Please select a product to add to the cart.");
        }
    }

    private void updateTotalPrice() {
        double total = cart.stream().mapToDouble(Product::getPrice).sum();
        double discount = 0;
        int quantity=1;
        try {

            discount = Double.parseDouble(discountField.getText());
            if (discount < 0 || discount > 100) {
                showError("Discount must be between 0 and 100.");
                return;
            }
        } catch (NumberFormatException e) {
            discount = 0;
        }

        quantity = Integer.parseInt(quantityField.getText());
        double discountedTotal = (total - (total * discount / 100))*quantity;
        totalPriceField.setText(String.valueOf(discountedTotal));
    }

    @FXML
    private void processSale() {
        if (cart.isEmpty()) {
            showError("The cart is empty. Please add products before processing the sale.");
            return;
        }

        try {
            int branchId = branchComboBox.getValue().getId();
            double totalPrice = Double.parseDouble(totalPriceField.getText());
            double discount = Double.parseDouble(discountField.getText());
            int quantity= Integer.parseInt(quantityField.getText());

            Sale sale = new Sale(0, new Date(), cart, totalPrice, discount, branchId, quantity);
            salesService.addSale(sale);

            cart.clear();
            updateTotalPrice();
            showSuccess("Sale processed successfully!");
        } catch (SQLException e) {
            showError("Failed to process sale: " + e.getMessage());
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