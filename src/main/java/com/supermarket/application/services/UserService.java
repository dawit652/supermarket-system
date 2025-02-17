package com.supermarket.application.services;

import com.supermarket.application.models.User;
import com.supermarket.data.dao.UserDAO;
import com.supermarket.utils.PasswordUtil;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Adds a new user with a hashed password.
     *
     * @param user The user to add.
     * @throws SQLException If a database error occurs.
     */
    public void addUser(User user) throws SQLException {
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword); // Replace plain-text password with hashed version
        userDAO.addUser(user);
    }

    /**
     * Updates an existing user with a hashed password if the password is updated.
     *
     * @param user The user to update.
     * @throws SQLException If a database error occurs.
     */


    /**
     * Fetches a user by username.
     *
     * @param username The username to search for.
     * @return The user object if found, null otherwise.
     * @throws SQLException If a database error occurs.
     */
    public User getUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    /**
     * Authenticates a user by verifying their hashed password.
     *
     * @param username The username to authenticate.
     * @param password The plain-text password to verify.
     * @return True if authentication succeeds, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public boolean authenticate(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            String storedHash = user.getPassword();
            System.out.println("Stored Hash: " + storedHash); // Log the stored hash
            System.out.println("Input Password: " + password); // Log the input password
            boolean isMatch = PasswordUtil.verifyPassword(password, storedHash);
            System.out.println("Password Match: " + isMatch); // Log the result of verification
            return isMatch;
        }
        return false;
    }
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers(); // Delegate to the DAO layer
    }

    public void updateUser(User user) throws SQLException {
        if (!user.getPassword().startsWith("$2")) { // Check if the password is already hashed
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            user.setPassword(hashedPassword); // Replace plain-text password with hashed version
        }
        userDAO.updateUser(user);
    }


    /**
     * Deletes a user from the database.
     *
     * @param id The ID of the user to delete.
     * @throws SQLException If a database error occurs.
     */
    public void deleteUser(int id) throws SQLException {
        userDAO.deleteUser(id);
    }
}