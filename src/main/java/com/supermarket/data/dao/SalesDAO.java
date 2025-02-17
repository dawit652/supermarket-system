package com.supermarket.data.dao;

import com.supermarket.application.models.Product;
import com.supermarket.application.models.Sale;
import com.supermarket.data.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesDAO {
    public void addSale(Sale sale) throws SQLException {
        String sql = "INSERT INTO sale (sale_date, total_price, discount, branch_id, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(sale.getSaleDate().getTime()));
            stmt.setDouble(2, sale.getTotalPrice());
            stmt.setDouble(3, sale.getDiscount());
            stmt.setInt(4, sale.getBranchId());
            stmt.setInt(5, sale.getQuantity());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int saleId = generatedKeys.getInt(1);
                    saveSaleProducts(saleId, sale.getProducts());
                }
            }
        }
    }

    private void saveSaleProducts(int saleId, List<Product> products) throws SQLException {
        String sql = "INSERT INTO sale_products (sale_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Product product : products) {
                stmt.setInt(1, saleId);
                stmt.setInt(2, product.getId());
                stmt.setInt(3, 1); // Assuming quantity is 1 for simplicity
                stmt.executeUpdate();
            }
        }
    }

    private void insertSaleProducts(Connection conn, int saleId, List<com.supermarket.application.models.Product> products) throws SQLException {
        String sql = "INSERT INTO sale_products (sale_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (com.supermarket.application.models.Product product : products) {
                stmt.setInt(1, saleId);
                stmt.setInt(2, product.getId());
                stmt.setInt(3, 1); // Assuming quantity is 1 for simplicity
                stmt.executeUpdate();
            }
        }
    }

    public List<Sale> getSalesByBranch(int branchId) throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales WHERE branch_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, branchId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sales.add(new Sale(
                            rs.getInt("id"),
                            rs.getDate("sale_date"),
                            null, // Products are not fetched here
                            rs.getDouble("total_price"),
                            rs.getDouble("discount"),
                            rs.getInt("branch_id"),
                            rs.getInt("quantity")
                    ));
                }
            }
        }
        return sales;
    }
    /**
     * Fetches all sales records from the database.
     *
     * @return A list of all sales.
     * @throws SQLException If a database error occurs.
     */
    public List<Sale> getAllSales() throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT id, sale_date, total_price, discount, branch_id FROM sale, quantity";
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
                sales.add(new Sale(id, saleDate, null, totalPrice, discount, branchId,quantity));
            }
        }
        return sales;
    }

    public List<Sale> getSalesByDate(Date date) throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales WHERE sale_date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            stmt.setDate(1, sqlDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = new Sale(
                            rs.getInt("id"),
                            rs.getDate("sale_date"),
                            null, // Products are not fetched here
                            rs.getDouble("total_price"),
                            rs.getDouble("discount"),
                            rs.getInt("branch_id"),
                            rs.getInt("quantity")
                    );
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching sales by date: " + e.getMessage());
            throw e;
        }
        return sales;
    }
}