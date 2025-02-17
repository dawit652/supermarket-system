package com.supermarket.utils;

public class ValidationUtil {
    public static boolean isValidPrice(double price) {
        return price > 0;
    }

    public static boolean isValidStock(int stock) {
        return stock >= 0;
    }
}