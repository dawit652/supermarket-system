package com.supermarket.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    /**
     * Hashes the given password using BCrypt.
     *
     * @param password Plain-text password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10)); // Cost factor of 10
    }

    /**
     * Verifies if the plain-text password matches the hashed password.
     *
     * @param plainPassword The plain-text password to verify.
     * @param hashedPassword The hashed password from the database.
     * @return True if the passwords match, false otherwise.
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2")) {
            System.err.println("Invalid hashed password format: " + hashedPassword);
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}