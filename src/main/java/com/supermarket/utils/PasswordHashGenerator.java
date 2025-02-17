package com.supermarket.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        String plainPassword = "admin123"; // Replace with the desired password
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(10)); // Cost factor of 10
        System.out.println("Hashed Password: " + hashedPassword);
    }
}