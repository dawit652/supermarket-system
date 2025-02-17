package com.supermarket.data.dao;

import com.supermarket.application.models.Product;
import com.supermarket.data.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    /**
     * Fetches all products with their stock levels for a specific branch.
     *
     * @param branchId The ID of the branch.
     * @return A list of products with stock levels.
     * @throws SQLException If a database error occurs.
     */
    public List<Product> getProductsByBranch(int branchId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, barcode, price, stock FROM product WHERE branch_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, branchId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String barcode = rs.getString("barcode");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");
                    products.add(new Product(id, name, barcode, price, stock, branchId));
                }
            }
        }
        return products;
    }

    /**
     * Updates the stock level of a product.
     *
     * @param product The product with updated stock level.
     * @throws SQLException If a database error occurs.
     */
    public void updateStock(Product product) throws SQLException {
        String sql = "UPDATE product SET stock = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getStock());
            stmt.setInt(2, product.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Fetches products with stock below a threshold.
     *
     * @param branchId The ID of the branch.
     * @param threshold The stock threshold.
     * @return A list of products with low stock.
     * @throws SQLException If a database error occurs.
     */
    public List<Product> getLowStockProducts(int branchId, int threshold) throws SQLException {
        List<Product> lowStockProducts = new ArrayList<>();
        String sql = "SELECT id, name, barcode, price, stock FROM product WHERE branch_id = ? AND stock <= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, branchId);
            stmt.setInt(2, threshold);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String barcode = rs.getString("barcode");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");
                    lowStockProducts.add(new Product(id, name, barcode, price, stock, branchId));
                }
            }
        }
        return lowStockProducts;
    }

    /**
     * Syncs offline inventory changes with the central database.
     *
     * @throws SQLException If a database error occurs.
     */
    public void syncOfflineChanges() throws SQLException {
        String sql = "INSERT INTO product (name, barcode, price, stock, branch_id) SELECT name, barcode, price, stock, branch_id FROM offline_product";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
}