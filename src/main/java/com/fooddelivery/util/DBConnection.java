package com.fooddelivery.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility Class Provides database connections for the Food
 * Delivery Application Uses credentials from web.xml context parameters
 */
public class DBConnection {

	// Database configuration - matches web.xml
	private static final String DB_URL = "jdbc:mysql://localhost:3306/food_delivery";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "Admin@123";
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

	// Static block to load the MySQL JDBC driver
	static {
		try {
			Class.forName(DB_DRIVER);
			System.out.println("✅ MySQL JDBC Driver loaded successfully");
		} catch (ClassNotFoundException e) {
			System.err.println("❌ MySQL JDBC Driver not found!");
			System.err.println("Please add mysql-connector-java.jar to WEB-INF/lib/");
			e.printStackTrace();
		}
	}

	/**
	 * Get a database connection
	 * 
	 * @return Connection object
	 * @throws SQLException if connection fails
	 */
	public static Connection getConnection() throws SQLException {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("✅ Database connection established to: " + DB_URL);
			return conn;
		} catch (SQLException e) {
			System.err.println("❌ Failed to create database connection!");
			System.err.println("URL: " + DB_URL);
			System.err.println("Username: " + DB_USERNAME);
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Close a database connection
	 * 
	 * @param connection Connection to close
	 */
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("✅ Database connection closed");
			} catch (SQLException e) {
				System.err.println("❌ Error closing database connection!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Test database connection
	 * 
	 * @return true if connection successful, false otherwise
	 */
	public static boolean testConnection() {
		try (Connection conn = getConnection()) {
			if (conn != null && !conn.isClosed()) {
				System.out.println("✅ Database connection test successful");
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.err.println("❌ Database connection test failed: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
