package com.supermarket.application.services;

import com.supermarket.application.models.Product;
import com.supermarket.data.dao.InventoryDAO;

import java.sql.SQLException;
import java.util.List;

public class InventoryService {
    private final InventoryDAO inventoryDAO;

    public InventoryService() {
        this.inventoryDAO = new InventoryDAO();
    }

    /**
     * Fetches all products with their stock levels for a specific branch.
     *
     * @param branchId The ID of the branch.
     * @return A list of products with stock levels.
     * @throws SQLException If a database error occurs.
     */
    public List<Product> getInventoryForBranch(int branchId) throws SQLException {
        return inventoryDAO.getProductsByBranch(branchId);
    }

    /**
     * Updates the stock level of a product.
     *
     * @param product The product with updated stock level.
     * @throws SQLException If a database error occurs.
     */
    public void updateStock(Product product) throws SQLException {
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }
        inventoryDAO.updateStock(product);
    }

    /**
     * Generates low-stock alerts for products below a threshold.
     *
     * @param branchId The ID of the branch.
     * @param threshold The stock threshold.
     * @return A list of products with low stock.
     * @throws SQLException If a database error occurs.
     */
    public List<Product> getLowStockAlerts(int branchId, int threshold) throws SQLException {
        return inventoryDAO.getLowStockProducts(branchId, threshold);
    }

    /**
     * Syncs offline inventory changes with the central database.
     *
     * @throws SQLException If a database error occurs.
     */
    public void syncOfflineInventory() throws SQLException {
        inventoryDAO.syncOfflineChanges();
    }
}