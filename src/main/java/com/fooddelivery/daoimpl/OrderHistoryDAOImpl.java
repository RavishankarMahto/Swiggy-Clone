package com.fooddelivery.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.dao.OrderHistoryDAO;
import com.fooddelivery.model.OrderHistory;
import com.fooddelivery.util.DBConnection;

/**
 * Order History DAO Implementation Implements database operations for
 * OrderHistory entity
 */
public class OrderHistoryDAOImpl implements OrderHistoryDAO {

	@Override
	public boolean addOrderHistory(OrderHistory orderHistory) {
		String sql = "INSERT INTO order_history (order_id, status, notes) VALUES (?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderHistory.getOrderId());
			pstmt.setString(2, orderHistory.getStatus());
			pstmt.setString(3, orderHistory.getNotes());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error adding order history: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public OrderHistory getOrderHistoryById(int historyId) {
		String sql = "SELECT * FROM order_history WHERE history_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, historyId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToOrderHistory(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error getting order history by ID: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<OrderHistory> getOrderHistoryByOrder(int orderId) {
		List<OrderHistory> historyEntries = new ArrayList<>();
		String sql = "SELECT * FROM order_history WHERE order_id = ? ORDER BY created_at ASC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				historyEntries.add(mapResultSetToOrderHistory(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting order history by order: " + e.getMessage());
			e.printStackTrace();
		}

		return historyEntries;
	}

	@Override
	public List<OrderHistory> getOrderHistoryByStatus(String status) {
		List<OrderHistory> historyEntries = new ArrayList<>();
		String sql = "SELECT * FROM order_history WHERE status = ? ORDER BY created_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, status);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				historyEntries.add(mapResultSetToOrderHistory(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting order history by status: " + e.getMessage());
			e.printStackTrace();
		}

		return historyEntries;
	}

	@Override
	public List<OrderHistory> getRecentOrderHistory(int limit) {
		List<OrderHistory> historyEntries = new ArrayList<>();
		String sql = "SELECT * FROM order_history ORDER BY created_at DESC LIMIT ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, limit);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				historyEntries.add(mapResultSetToOrderHistory(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting recent order history: " + e.getMessage());
			e.printStackTrace();
		}

		return historyEntries;
	}

	@Override
	public List<OrderHistory> getOrderHistoryByDateRange(Timestamp startDate, Timestamp endDate) {
		List<OrderHistory> historyEntries = new ArrayList<>();
		String sql = "SELECT * FROM order_history WHERE created_at BETWEEN ? AND ? ORDER BY created_at ASC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setTimestamp(1, startDate);
			pstmt.setTimestamp(2, endDate);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				historyEntries.add(mapResultSetToOrderHistory(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting order history by date range: " + e.getMessage());
			e.printStackTrace();
		}

		return historyEntries;
	}

	@Override
	public boolean deleteOrderHistory(int historyId) {
		String sql = "DELETE FROM order_history WHERE history_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, historyId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error deleting order history: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteOrderHistoryByOrder(int orderId) {
		String sql = "DELETE FROM order_history WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected >= 0; // 0 or more rows affected is acceptable

		} catch (SQLException e) {
			System.err.println("Error deleting order history by order: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getOrderHistoryCount(int orderId) {
		String sql = "SELECT COUNT(*) as history_count FROM order_history WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("history_count");
			}

		} catch (SQLException e) {
			System.err.println("Error getting order history count: " + e.getMessage());
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Helper method to map ResultSet to OrderHistory object
	 */
	private OrderHistory mapResultSetToOrderHistory(ResultSet rs) throws SQLException {
		OrderHistory orderHistory = new OrderHistory();
		orderHistory.setHistoryId(rs.getInt("history_id"));
		orderHistory.setOrderId(rs.getInt("order_id"));
		orderHistory.setStatus(rs.getString("status"));
		orderHistory.setNotes(rs.getString("notes"));
		orderHistory.setCreatedAt(rs.getTimestamp("created_at"));
		return orderHistory;
	}
}