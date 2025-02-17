package com.supermarket.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationUtil {
    private static Locale currentLocale = Locale.ENGLISH; // Default language is English

    /**
     * Sets the current language based on the locale.
     *
     * @param languageTag The language tag (e.g., "en" for English, "am" for Amharic).
     */
    public static void setLanguage(String languageTag) {
        if ("en".equalsIgnoreCase(languageTag)) {
            currentLocale = Locale.ENGLISH; // Set to English
        } else if ("am".equalsIgnoreCase(languageTag)) {
            currentLocale = new Locale("am"); // Set to Amharic
        } else {
            System.err.println("Unsupported language: " + languageTag);
        }
    }

    /**
     * Gets the currently selected language's locale.
     *
     * @return The current locale (e.g., Locale.ENGLISH or Locale.forLanguageTag("am")).
     */
    public static String getCurrentLanguage() {
        return currentLocale.getLanguage(); // Return the language code (e.g., "en", "am").
    }

    /**
     * Retrieves a localized string from the resource bundle.
     *
     * @param key The key for the string in the resource bundle.
     * @param args Optional arguments to replace placeholders in the string.
     * @return The localized string with placeholders replaced.
     */
    public static String getLocalizedString(String key, Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("localization.messages", currentLocale);
        try {
            String message = bundle.getString(key); // Get the base string
            return String.format(message, args); // Replace placeholders with arguments
        } catch (Exception e) {
            System.err.println("Missing key '" + key + "' in locale " + currentLocale + ". Falling back to default.");
            return bundle.getString("default_error_message");
        }
    }
}