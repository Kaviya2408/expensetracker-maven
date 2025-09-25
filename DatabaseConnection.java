package com.expnsetracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    public static final String URL = "jdbc:mysql://localhost:3306/expensetracker";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "2624";

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    public static Connection getDBConnection() throws SQLException {
        try {
            // Optional: explicitly load driver (not required for modern JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
