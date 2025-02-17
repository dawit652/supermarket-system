package com.supermarket.data.dao;

import com.supermarket.application.models.Sale;
import com.supermarket.application.models.Product;
import com.supermarket.data.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    /**
     * Fetches all sales records from the database.
     *
     * @return A list of all sales.
     * @throws SQLException If a database error occurs.
     */
    public List<Sale> getAllSales() throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT id, sale_date, total_price, discount, branch_id FROM sale";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Date saleDate = rs.getDate("sale_date");
                double totalPrice = rs.getDouble("total_price");
                double discount = rs.getDouble("discount");
                int branchId = rs.getInt("branch_id");
                int quantity=rs.getInt("quantity");
                sales.add(new Sale(id, saleDate, null, totalPrice, discount, branchId, quantity));
            }
        }
        return sales;
    }

    /**
     * Fetches all products for a specific branch.
     *
     * @param branchId The ID of the branch.
     * @return A list of all products in the branch.
     * @throws SQLException If a database error occurs.
     */
    public List<Product> getAllProducts(int branchId) throws SQLException {
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
}