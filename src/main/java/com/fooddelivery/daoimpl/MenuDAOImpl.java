package com.fooddelivery.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.dao.MenuDAO;
import com.fooddelivery.model.Menu;
import com.fooddelivery.util.DBConnection;

/**
 * Menu DAO Implementation Implements database operations for Menu entity
 */
public class MenuDAOImpl implements MenuDAO {

	@Override
	public boolean addMenuItem(Menu menuItem) {
		String sql = "INSERT INTO menu_items (restaurant_id, name, description, price, category, image_url, is_available) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, menuItem.getRestaurantId());
			pstmt.setString(2, menuItem.getName());
			pstmt.setString(3, menuItem.getDescription());
			pstmt.setBigDecimal(4, menuItem.getPrice());
			pstmt.setString(5, menuItem.getCategory());
			pstmt.setString(6, menuItem.getImageUrl());
			pstmt.setBoolean(7, menuItem.isAvailable());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error adding menu item: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Menu getMenuItemById(int itemId) {
		String sql = "SELECT * FROM menu_items WHERE item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, itemId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToMenu(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error getting menu item by ID: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Menu> getMenuItemsByRestaurant(int restaurantId) {
		List<Menu> menuItems = new ArrayList<>();
		String sql = "SELECT * FROM menu_items WHERE restaurant_id = ? ORDER BY category, name";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				menuItems.add(mapResultSetToMenu(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting menu items by restaurant: " + e.getMessage());
			e.printStackTrace();
		}

		return menuItems;
	}

	@Override
	public List<Menu> getAvailableMenuItemsByRestaurant(int restaurantId) {
		List<Menu> menuItems = new ArrayList<>();
		String sql = "SELECT * FROM menu_items WHERE restaurant_id = ? AND is_available = true ORDER BY category, name";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				menuItems.add(mapResultSetToMenu(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting available menu items by restaurant: " + e.getMessage());
			e.printStackTrace();
		}

		return menuItems;
	}

	@Override
	public List<Menu> getMenuItemsByCategory(int restaurantId, String category) {
		List<Menu> menuItems = new ArrayList<>();
		String sql = "SELECT * FROM menu_items WHERE restaurant_id = ? AND category = ? AND is_available = true ORDER BY name";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);
			pstmt.setString(2, category);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				menuItems.add(mapResultSetToMenu(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting menu items by category: " + e.getMessage());
			e.printStackTrace();
		}

		return menuItems;
	}

	@Override
	public List<Menu> searchMenuItems(int restaurantId, String searchTerm) {
		List<Menu> menuItems = new ArrayList<>();
		String sql = "SELECT * FROM menu_items WHERE restaurant_id = ? AND (name LIKE ? OR description LIKE ? OR category LIKE ?) AND is_available = true ORDER BY name";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			String searchPattern = "%" + searchTerm + "%";
			pstmt.setInt(1, restaurantId);
			pstmt.setString(2, searchPattern);
			pstmt.setString(3, searchPattern);
			pstmt.setString(4, searchPattern);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				menuItems.add(mapResultSetToMenu(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error searching menu items: " + e.getMessage());
			e.printStackTrace();
		}

		return menuItems;
	}

	@Override
	public boolean updateMenuItem(Menu menuItem) {
		String sql = "UPDATE menu_items SET name=?, description=?, price=?, category=?, image_url=?, is_available=? WHERE item_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, menuItem.getName());
			pstmt.setString(2, menuItem.getDescription());
			pstmt.setBigDecimal(3, menuItem.getPrice());
			pstmt.setString(4, menuItem.getCategory());
			pstmt.setString(5, menuItem.getImageUrl());
			pstmt.setBoolean(6, menuItem.isAvailable());
			pstmt.setInt(7, menuItem.getItemId());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error updating menu item: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteMenuItem(int itemId) {
		String sql = "DELETE FROM menu_items WHERE item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, itemId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error deleting menu item: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean setMenuItemAvailability(int itemId, boolean isAvailable) {
		String sql = "UPDATE menu_items SET is_available = ? WHERE item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setBoolean(1, isAvailable);
			pstmt.setInt(2, itemId);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error setting menu item availability: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<String> getCategoriesByRestaurant(int restaurantId) {
		List<String> categories = new ArrayList<>();
		String sql = "SELECT DISTINCT category FROM menu_items WHERE restaurant_id = ? AND is_available = true ORDER BY category";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				categories.add(rs.getString("category"));
			}

		} catch (SQLException e) {
			System.err.println("Error getting categories by restaurant: " + e.getMessage());
			e.printStackTrace();
		}

		return categories;
	}

	/**
	 * Helper method to map ResultSet to Menu object
	 */
	private Menu mapResultSetToMenu(ResultSet rs) throws SQLException {
		Menu menu = new Menu();
		menu.setItemId(rs.getInt("item_id"));
		menu.setRestaurantId(rs.getInt("restaurant_id"));
		menu.setName(rs.getString("name"));
		menu.setDescription(rs.getString("description"));
		menu.setPrice(rs.getBigDecimal("price"));
		menu.setCategory(rs.getString("category"));
		menu.setImageUrl(rs.getString("image_url"));
		menu.setAvailable(rs.getBoolean("is_available"));
		menu.setCreatedAt(rs.getTimestamp("created_at"));
		menu.setUpdatedAt(rs.getTimestamp("updated_at"));
		return menu;
	}
}