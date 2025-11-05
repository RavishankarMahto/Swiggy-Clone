package com.fooddelivery.daoimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.dao.OrderItemDAO;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.util.DBConnection;

/**
 * Order Item DAO Implementation Implements database operations for OrderItem
 * entity
 */
public class OrderItemDAOImpl implements OrderItemDAO {

	@Override
	public boolean addOrderItem(OrderItem orderItem) {
		String sql = "INSERT INTO order_items (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderItem.getOrderId());
			pstmt.setInt(2, orderItem.getItemId());
			pstmt.setInt(3, orderItem.getQuantity());
			pstmt.setBigDecimal(4, orderItem.getPrice());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error adding order item: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public OrderItem getOrderItemById(int orderItemId) {
		String sql = "SELECT oi.*, mi.name as item_name, mi.description as item_description " + "FROM order_items oi "
				+ "JOIN menu_items mi ON oi.item_id = mi.item_id " + "WHERE oi.order_item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderItemId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToOrderItem(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error getting order item by ID: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<OrderItem> getOrderItemsByOrder(int orderId) {
		List<OrderItem> orderItems = new ArrayList<>();
		String sql = "SELECT oi.*, mi.name as item_name, mi.description as item_description " + "FROM order_items oi "
				+ "JOIN menu_items mi ON oi.item_id = mi.item_id " + "WHERE oi.order_id = ? ORDER BY oi.created_at ASC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				orderItems.add(mapResultSetToOrderItem(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting order items by order: " + e.getMessage());
			e.printStackTrace();
		}

		return orderItems;
	}

	@Override
	public boolean updateOrderItemQuantity(int orderItemId, int quantity) {
		String sql = "UPDATE order_items SET quantity = ? WHERE order_item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, quantity);
			pstmt.setInt(2, orderItemId);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error updating order item quantity: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateOrderItemPrice(int orderItemId, double price) {
		String sql = "UPDATE order_items SET price = ? WHERE order_item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setBigDecimal(1, BigDecimal.valueOf(price));
			pstmt.setInt(2, orderItemId);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error updating order item price: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeOrderItem(int orderItemId) {
		String sql = "DELETE FROM order_items WHERE order_item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderItemId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error removing order item: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeAllOrderItems(int orderId) {
		String sql = "DELETE FROM order_items WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected >= 0; // 0 or more rows affected is acceptable

		} catch (SQLException e) {
			System.err.println("Error removing all order items: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getTotalQuantityByOrder(int orderId) {
		String sql = "SELECT COALESCE(SUM(quantity), 0) as total_quantity FROM order_items WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("total_quantity");
			}

		} catch (SQLException e) {
			System.err.println("Error getting total quantity by order: " + e.getMessage());
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public double getTotalPriceByOrder(int orderId) {
		String sql = "SELECT COALESCE(SUM(quantity * price), 0) as total_price FROM order_items WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getDouble("total_price");
			}

		} catch (SQLException e) {
			System.err.println("Error getting total price by order: " + e.getMessage());
			e.printStackTrace();
		}

		return 0.0;
	}

	/**
	 * Helper method to map ResultSet to OrderItem object
	 */
	private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderItemId(rs.getInt("order_item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		orderItem.setPrice(rs.getBigDecimal("price"));
		orderItem.setCreatedAt(rs.getTimestamp("created_at"));

		// Set additional display fields if available
		try {
			orderItem.setItemName(rs.getString("item_name"));
			orderItem.setItemDescription(rs.getString("item_description"));
		} catch (SQLException e) {
			// These fields might not be available in all queries
		}

		return orderItem;
	}
}