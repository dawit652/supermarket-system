package com.supermarket.data;

import com.supermarket.application.models.User;
import com.supermarket.application.services.UserService;
import com.supermarket.utils.PasswordUtil;

import java.sql.SQLException;
import java.util.List;

public class PasswordUpdater {
    public static void main(String[] args) {
        UserService userService = new UserService();
        try {
            List<User> users = userService.getAllUsers(); // Fetch all users
            for (User user : users) {
                String storedPassword = user.getPassword();
                if (!storedPassword.startsWith("$2")) { // Check if the password is already hashed
                    String hashedPassword = PasswordUtil.hashPassword(storedPassword); // Hash the password
                    user.setPassword(hashedPassword); // Update the user object with the hashed password
                    userService.updateUser(user); // Save the updated user back to the database
                    System.out.println("Updated password for user: " + user.getUsername());
                } else {
                    System.out.println("Password already hashed for user: " + user.getUsername());
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to update passwords: " + e.getMessage());
        }
    }
}