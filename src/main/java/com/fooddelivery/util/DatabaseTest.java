package com.fooddelivery.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Database Test Class Tests database connection and basic operations
 */
public class DatabaseTest {

	public static void main(String[] args) {
		System.out.println("🔧 Testing Database Connection...\n");

		// Test 1: Basic Connection
		testBasicConnection();

		// Test 2: Database Schema
		testDatabaseSchema();

		// Test 3: Sample Data
		testSampleData();

		// Test 4: DAO Operations
		testDAOOperations();

		System.out.println("\n✅ Database testing completed!");
	}

	/**
	 * Test basic database connection
	 */
	private static void testBasicConnection() {
		System.out.println("📡 Testing basic connection...");

		try (Connection conn = DBConnection.getConnection()) {
			if (conn != null && !conn.isClosed()) {
				System.out.println("   ✅ Connection successful!");
				System.out.println("   📊 Database URL: " + conn.getMetaData().getURL());
				System.out.println("   👤 Database User: " + conn.getMetaData().getUserName());
			} else {
				System.err.println("   ❌ Connection failed - connection is null or closed");
			}
		} catch (Exception e) {
			System.err.println("   ❌ Connection failed: " + e.getMessage());
		}
	}

	/**
	 * Test database schema (tables exist)
	 */
	private static void testDatabaseSchema() {
		System.out.println("\n📋 Testing database schema...");

		String[] expectedTables = { "users", "restaurants", "menu_items", "orders", "order_items", "order_history" };

		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SHOW TABLES")) {

			System.out.println("   📊 Found tables:");
			while (rs.next()) {
				String tableName = rs.getString(1);
				System.out.println("      ✅ " + tableName);
			}

		} catch (Exception e) {
			System.err.println("   ❌ Schema test failed: " + e.getMessage());
		}
	}

	/**
	 * Test sample data
	 */
	private static void testSampleData() {
		System.out.println("\n📊 Testing sample data...");

		try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {

			// Test users
			try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users")) {
				if (rs.next()) {
					int userCount = rs.getInt(1);
					System.out.println("   👥 Users: " + userCount);
				}
			}

			// Test restaurants
			try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM restaurants")) {
				if (rs.next()) {
					int restaurantCount = rs.getInt(1);
					System.out.println("   🏪 Restaurants: " + restaurantCount);
				}
			}

			// Test menu items
			try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM menu_items")) {
				if (rs.next()) {
					int menuItemCount = rs.getInt(1);
					System.out.println("   🍽️ Menu Items: " + menuItemCount);
				}
			}

			// Test admin user
			try (ResultSet rs = stmt.executeQuery("SELECT username, role FROM users WHERE username = 'admin'")) {
				if (rs.next()) {
					System.out.println(
							"   👑 Admin user: " + rs.getString("username") + " (" + rs.getString("role") + ")");
				}
			}

		} catch (Exception e) {
			System.err.println("   ❌ Sample data test failed: " + e.getMessage());
		}
	}

	/**
	 * Test DAO operations
	 */
	private static void testDAOOperations() {
		System.out.println("\n🔧 Testing DAO operations...");

		try {
			// Test UserDAO
			com.fooddelivery.daoimpl.UserDAOImpl userDAO = new com.fooddelivery.daoimpl.UserDAOImpl();
			com.fooddelivery.model.User adminUser = userDAO.getUserByUsername("admin");

			if (adminUser != null) {
				System.out.println("   ✅ UserDAO: Found admin user - " + adminUser.getEmail());
			} else {
				System.out.println("   ❌ UserDAO: Admin user not found");
			}

			// Test RestaurantDAO
			com.fooddelivery.daoimpl.RestaurantDAOImpl restaurantDAO = new com.fooddelivery.daoimpl.RestaurantDAOImpl();
			java.util.List<com.fooddelivery.model.Restaurant> restaurants = restaurantDAO.getActiveRestaurants();

			if (restaurants != null && !restaurants.isEmpty()) {
				System.out.println("   ✅ RestaurantDAO: Found " + restaurants.size() + " active restaurants");
			} else {
				System.out.println("   ❌ RestaurantDAO: No restaurants found");
			}

			// Test MenuDAO
			com.fooddelivery.daoimpl.MenuDAOImpl menuDAO = new com.fooddelivery.daoimpl.MenuDAOImpl();
			if (!restaurants.isEmpty()) {
				java.util.List<com.fooddelivery.model.Menu> menuItems = menuDAO
						.getMenuItemsByRestaurant(restaurants.get(0).getRestaurantId());
				System.out.println("   ✅ MenuDAO: Found " + menuItems.size() + " menu items for first restaurant");
			}

		} catch (Exception e) {
			System.err.println("   ❌ DAO operations test failed: " + e.getMessage());
		}
	}
}
