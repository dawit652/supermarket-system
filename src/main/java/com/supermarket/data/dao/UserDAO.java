package com.supermarket.data.dao;

import com.supermarket.application.models.User;
import com.supermarket.data.DatabaseConnection;
import com.supermarket.utils.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    /**
     * Adds a new user to the database.
     *
     * @param user The user to add.
     * @throws SQLException If a database error occurs.
     */
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, role, branch_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Password is already hashed
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getBranchId());
            stmt.executeUpdate();
        }
    }

    /**
     * Fetches a user by username.
     *
     * @param username The username to search for.
     * @return The user object if found, null otherwise.
     * @throws SQLException If a database error occurs.
     */
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"), // Password is stored as a hash
                            rs.getString("role"),
                            rs.getInt("branch_id")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The user to update.
     * @throws SQLException If a database error occurs.
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getInt("branch_id")
                ));
            }
        }
        return users;
    }

    public void updateUser(User user) throws SQLException {
        if (!user.getPassword().startsWith("$2")) { // Check if the password is already hashed
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            user.setPassword(hashedPassword); // Replace plain-text password with hashed version
        }
        String sql = "UPDATE user SET username = ?, password = ?, role = ?, branch_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getBranchId());
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param id The ID of the user to delete.
     * @throws SQLException If a database error occurs.
     */
    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}