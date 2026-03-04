package org.jdbc_trial_spring_boot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Standardize these for your team!
    private static final String URL = "jdbc:mysql://localhost:3306/ada_db";
    private static final String USER = "root";
    private static final String PASS = "root"; // Update to your local MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            // Loading the driver explicitly for Tomcat compatibility
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found in classpath", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}