package com.supermarket.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static final String DATABASE_NAME = "supermarket.db";
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getDatabaseURL());
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(10000);
        config.setAutoCommit(true);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static String getDatabaseURL() {
        // Absolute path for development (adjust based on your system)
        String dbPath = "C:\\Users\\dawit\\Desktop\\supermarket-system\\src\\main\\resources\\supermarket.db";

        // Check if the file exists
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            logger.error("Database file not found at: {}", dbPath);
            throw new RuntimeException("Database file not found at: " + dbPath);
        }

        // Return the JDBC URL for SQLite
        return "jdbc:sqlite:" + dbPath;
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
            logger.info("Database connection pool closed.");
        }
    }
}