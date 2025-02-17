package com.supermarket.data.dao;

import com.supermarket.application.models.Branch;
import com.supermarket.data.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {
    /**
     * Fetches all branches from the database.
     *
     * @return A list of all branches.
     * @throws SQLException If a database error occurs.
     */
    public List<Branch> getAllBranches() throws SQLException {
        List<Branch> branches = new ArrayList<>();
        String sql = "SELECT id, name, location FROM branches"; // Use 'branches' instead of 'branch'
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String location = rs.getString("location");
                branches.add(new Branch(id, name, location));
            }
        }
        return branches;
    }

    /**
     * Adds a new branch to the database.
     *
     * @param branch The branch to add.
     * @throws SQLException If a database error occurs.
     */
    public void addBranch(Branch branch) throws SQLException {
        String sql = "INSERT INTO branches (name, location) VALUES (?, ?)"; // Use 'branches' instead of 'branch'
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, branch.getName());
            stmt.setString(2, branch.getLocation());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    branch.setId(generatedKeys.getInt(1)); // Set the generated ID for the branch object
                }
            }
        }
    }

    /**
     * Updates an existing branch in the database.
     *
     * @param branch The branch to update.
     * @throws SQLException If a database error occurs.
     */
    public void updateBranch(Branch branch) throws SQLException {
        String sql = "UPDATE branches SET name = ?, location = ? WHERE id = ?"; // Use 'branches' instead of 'branch'
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, branch.getName());
            stmt.setString(2, branch.getLocation());
            stmt.setInt(3, branch.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a branch from the database by ID.
     *
     * @param id The ID of the branch to delete.
     * @throws SQLException If a database error occurs.
     */
    public void deleteBranch(int id) throws SQLException {
        String sql = "DELETE FROM branches WHERE id = ?"; // Use 'branches' instead of 'branch'
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}