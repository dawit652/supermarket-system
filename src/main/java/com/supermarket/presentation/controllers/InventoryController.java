package com.supermarket.presentation.controllers;

import com.supermarket.application.models.Branch;
import com.supermarket.application.services.BranchService;
import com.supermarket.application.services.ProductService;
import com.supermarket.application.models.Product;
import com.supermarket.presentation.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;

public class InventoryController {
    @FXML
    private ComboBox<Branch> branchComboBox; // Ensure this matches the fx:id in FXML
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> barcodeColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> stockColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField barcodeField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;
    private App app;

    private BranchService branchService = new BranchService();
    private final ProductService productService = new ProductService();
    private final ObservableList<Product> products = FXCollections.observableArrayList();



    @FXML
    public void initialize() {
        System.out.println("Initializing InventoryController...");

        // Set up table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));



        // Add Actions column with Update and Delete buttons
        TableColumn<Product, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(180);
        actionsColumn.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {
                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");

                    {
                        // Update button action
                        updateButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            updateProduct(product);
                        });

                        // Delete button action
                        deleteButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            deleteProduct(product);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(5, updateButton, deleteButton);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        });

        // Add the Actions column to the table
        productTable.getColumns().add(actionsColumn);

        // Add listener for product selection
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Populate input fields with selected product's data
                nameField.setText(newValue.getName());
                barcodeField.setText(newValue.getBarcode());
                priceField.setText(String.valueOf(newValue.getPrice()));
                stockField.setText(String.valueOf(newValue.getStock()));
            }
        });

        try {
            // Load branches into the ComboBox
            List<Branch> branches = branchService.getAllBranches();
            branchComboBox.setItems(FXCollections.observableArrayList(branches));
            System.out.println("Branches loaded: " + branches.size());

            // Add listener for branch selection
            branchComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    System.out.println("Selected branch: " + newValue.getName());
                    loadProducts(newValue.getId());
                }
            });
        } catch (SQLException e) {
            showError("Failed to load branches: " + e.getMessage());
        }
    }

    public void setApp(App app) {
        this.app = app;
    }

    private void loadProducts(int branchId) {
        try {
            products.clear();
            List<Product> productList = productService.getAllProducts(branchId);
            products.addAll(productList);
            productTable.setItems(products);
            System.out.println("Products loaded: " + productList.size());
        } catch (SQLException e) {
            showError("Failed to load products: " + e.getMessage());
        }
    }

    @FXML
    private void addProduct() {
        try {
            String name = nameField.getText();
            String barcode = barcodeField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            int branchId = branchComboBox.getValue().getId();

            Product product = new Product(name, barcode, price, stock, branchId);
            productService.addProduct(product);
            loadProducts(branchId);

            // Clear input fields
            nameField.clear();
            barcodeField.clear();
            priceField.clear();
            stockField.clear();
        } catch (NumberFormatException e) {
            showError("Invalid input. Please check the price and stock fields.");
        } catch (SQLException e) {
            showError("Failed to add product: " + e.getMessage());
        } catch (NullPointerException e) {
            showError("Please select a branch before adding a product.");
        }
    }
    @FXML
    private void updateProduct(Product product) {
        try {
            // Get updated values from input fields
            String name = nameField.getText();
            String barcode = barcodeField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            // Update the selected product
            product.setName(name);
            product.setBarcode(barcode);
            product.setPrice(price);
            product.setStock(stock);

            // Save changes to the database
            productService.updateProduct(product);

            // Refresh the table
            loadProducts(product.getBranchId());

            // Clear input fields
            nameField.clear();
            barcodeField.clear();
            priceField.clear();
            stockField.clear();
        } catch (NumberFormatException e) {
            showError("Invalid input. Please check the price and stock fields.");
        } catch (SQLException e) {
            showError("Failed to update product: " + e.getMessage());
        }
    }

    @FXML
    private void deleteProduct(Product product) {
        try {
            // Delete the selected product from the database
            productService.deleteProduct(product.getId());

            // Refresh the table
            loadProducts(product.getBranchId());

            // Clear input fields
            nameField.clear();
            barcodeField.clear();
            priceField.clear();
            stockField.clear();
        } catch (SQLException e) {
            showError("Failed to delete product: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}