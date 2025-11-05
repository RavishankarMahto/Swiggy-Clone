package com.fooddelivery.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.model.Order;
import com.fooddelivery.util.DBConnection;

/**
 * Order DAO Implementation Implements database operations for Order entity
 */
public class OrderDAOImpl implements OrderDAO {

	@Override
	public int createOrder(Order order) {
		String sql = "INSERT INTO orders (user_id, restaurant_id, total_amount, delivery_address, status, payment_status, delivery_fee, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setInt(1, order.getUserId());
			pstmt.setInt(2, order.getRestaurantId());
			pstmt.setBigDecimal(3, order.getTotalAmount());
			pstmt.setString(4, order.getDeliveryAddress());
			pstmt.setString(5, order.getStatus());
			pstmt.setString(6, order.getPaymentStatus());
			pstmt.setBigDecimal(7, order.getDeliveryFee());
			pstmt.setString(8, order.getNotes());

			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet generatedKeys = pstmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}

		} catch (SQLException e) {
			System.err.println("Error creating order: " + e.getMessage());
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public Order getOrderById(int orderId) {
		String sql = "SELECT o.*, u.username as user_name, r.name as restaurant_name " + "FROM orders o "
				+ "JOIN users u ON o.user_id = u.user_id " + "JOIN restaurants r ON o.restaurant_id = r.restaurant_id "
				+ "WHERE o.order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToOrder(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error getting order by ID: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Order> getOrdersByUser(int userId) {
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT o.*, u.username as user_name, r.name as restaurant_name " + "FROM orders o "
				+ "JOIN users u ON o.user_id = u.user_id " + "JOIN restaurants r ON o.restaurant_id = r.restaurant_id "
				+ "WHERE o.user_id = ? ORDER BY o.order_date DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				orders.add(mapResultSetToOrder(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting orders by user: " + e.getMessage());
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public List<Order> getOrdersByRestaurant(int restaurantId) {
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT o.*, u.username as user_name, r.name as restaurant_name " + "FROM orders o "
				+ "JOIN users u ON o.user_id = u.user_id " + "JOIN restaurants r ON o.restaurant_id = r.restaurant_id "
				+ "WHERE o.restaurant_id = ? ORDER BY o.order_date DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				orders.add(mapResultSetToOrder(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting orders by restaurant: " + e.getMessage());
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public List<Order> getOrdersByStatus(String status) {
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT o.*, u.username as user_name, r.name as restaurant_name " + "FROM orders o "
				+ "JOIN users u ON o.user_id = u.user_id " + "JOIN restaurants r ON o.restaurant_id = r.restaurant_id "
				+ "WHERE o.status = ? ORDER BY o.order_date DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, status);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				orders.add(mapResultSetToOrder(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting orders by status: " + e.getMessage());
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public List<Order> getOrdersByPaymentStatus(String paymentStatus) {
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT o.*, u.username as user_name, r.name as restaurant_name " + "FROM orders o "
				+ "JOIN users u ON o.user_id = u.user_id " + "JOIN restaurants r ON o.restaurant_id = r.restaurant_id "
				+ "WHERE o.payment_status = ? ORDER BY o.order_date DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, paymentStatus);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				orders.add(mapResultSetToOrder(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting orders by payment status: " + e.getMessage());
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public boolean updateOrderStatus(int orderId, String status) {
		String sql = "UPDATE orders SET status = ?, updated_at = CURRENT_TIMESTAMP WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, status);
			pstmt.setInt(2, orderId);

			int rowsAffected = pstmt.executeUpdate();

			// Add to order history
			if (rowsAffected > 0) {
				addOrderHistory(orderId, status, "Status updated to: " + status);
			}

			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error updating order status: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updatePaymentStatus(int orderId, String paymentStatus) {
		String sql = "UPDATE orders SET payment_status = ?, updated_at = CURRENT_TIMESTAMP WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, paymentStatus);
			pstmt.setInt(2, orderId);

			int rowsAffected = pstmt.executeUpdate();

			// Add to order history
			if (rowsAffected > 0) {
				addOrderHistory(orderId, paymentStatus, "Payment status updated to: " + paymentStatus);
			}

			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error updating payment status: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateOrder(Order order) {
		String sql = "UPDATE orders SET user_id=?, restaurant_id=?, total_amount=?, delivery_address=?, status=?, payment_status=?, delivery_fee=?, notes=? WHERE order_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, order.getUserId());
			pstmt.setInt(2, order.getRestaurantId());
			pstmt.setBigDecimal(3, order.getTotalAmount());
			pstmt.setString(4, order.getDeliveryAddress());
			pstmt.setString(5, order.getStatus());
			pstmt.setString(6, order.getPaymentStatus());
			pstmt.setBigDecimal(7, order.getDeliveryFee());
			pstmt.setString(8, order.getNotes());
			pstmt.setInt(9, order.getOrderId());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error updating order: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean cancelOrder(int orderId) {
		String sql = "UPDATE orders SET status = 'CANCELLED', updated_at = CURRENT_TIMESTAMP WHERE order_id = ? AND status != 'DELIVERED'";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);

			int rowsAffected = pstmt.executeUpdate();

			// Add to order history
			if (rowsAffected > 0) {
				addOrderHistory(orderId, "CANCELLED", "Order cancelled by user");
			}

			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error cancelling order: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Order> getOrderHistory(int orderId) {
		List<Order> history = new ArrayList<>();
		String sql = "SELECT * FROM order_history WHERE order_id = ? ORDER BY created_at ASC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Order historyEntry = new Order();
				historyEntry.setOrderId(rs.getInt("order_id"));
				historyEntry.setStatus(rs.getString("status"));
				historyEntry.setNotes(rs.getString("notes"));
				historyEntry.setCreatedAt(rs.getTimestamp("created_at"));
				history.add(historyEntry);
			}

		} catch (SQLException e) {
			System.err.println("Error getting order history: " + e.getMessage());
			e.printStackTrace();
		}

		return history;
	}

	@Override
	public double getTotalSalesByRestaurant(int restaurantId) {
		String sql = "SELECT COALESCE(SUM(total_amount), 0) as total_sales FROM orders WHERE restaurant_id = ? AND payment_status = 'PAID'";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, restaurantId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getDouble("total_sales");
			}

		} catch (SQLException e) {
			System.err.println("Error getting total sales by restaurant: " + e.getMessage());
			e.printStackTrace();
		}

		return 0.0;
	}

	@Override
	public int getOrderCountByStatus(String status) {
		String sql = "SELECT COUNT(*) as order_count FROM orders WHERE status = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, status);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("order_count");
			}

		} catch (SQLException e) {
			System.err.println("Error getting order count by status: " + e.getMessage());
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Helper method to add entry to order history
	 */
	private void addOrderHistory(int orderId, String status, String notes) {
		String sql = "INSERT INTO order_history (order_id, status, notes) VALUES (?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			pstmt.setString(2, status);
			pstmt.setString(3, notes);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println("Error adding order history: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Helper method to map ResultSet to Order object
	 */
	private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setOrderId(rs.getInt("order_id"));
		order.setUserId(rs.getInt("user_id"));
		order.setRestaurantId(rs.getInt("restaurant_id"));
		order.setOrderDate(rs.getTimestamp("order_date"));
		order.setTotalAmount(rs.getBigDecimal("total_amount"));
		order.setDeliveryAddress(rs.getString("delivery_address"));
		order.setStatus(rs.getString("status"));
		order.setPaymentStatus(rs.getString("payment_status"));
		order.setDeliveryFee(rs.getBigDecimal("delivery_fee"));
		order.setNotes(rs.getString("notes"));
		order.setCreatedAt(rs.getTimestamp("created_at"));
		order.setUpdatedAt(rs.getTimestamp("updated_at"));

		// Set additional display fields if available
		try {
			order.setUserName(rs.getString("user_name"));
			order.setRestaurantName(rs.getString("restaurant_name"));
		} catch (SQLException e) {
			// These fields might not be available in all queries
		}

		return order;
	}
}