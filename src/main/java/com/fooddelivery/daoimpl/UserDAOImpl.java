package com.fooddelivery.daoimpl;

import com.fooddelivery.dao.UserDAO;
import com.fooddelivery.model.User;
import com.fooddelivery.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User DAO Implementation Implements database operations for User entity
 */
public class UserDAOImpl implements UserDAO {

	@Override
	public boolean addUser(User user) {
		String sql = "INSERT INTO users (username, password, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getPhone());
			pstmt.setString(5, user.getAddress());
			pstmt.setString(6, user.getRole());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error adding user: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToUser(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error getting user by ID: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToUser(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error getting user by username: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToUser(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error getting user by email: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean updateUser(User user) {
		String sql = "UPDATE users SET username=?, password=?, email=?, phone=?, address=?, role=? WHERE user_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getPhone());
			pstmt.setString(5, user.getAddress());
			pstmt.setString(6, user.getRole());
			pstmt.setInt(7, user.getUserId());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error updating user: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(int userId) {
		String sql = "DELETE FROM users WHERE user_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error deleting user: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				users.add(mapResultSetToUser(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting all users: " + e.getMessage());
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public List<User> getUsersByRole(String role) {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users WHERE role = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, role);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				users.add(mapResultSetToUser(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error getting users by role: " + e.getMessage());
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public User authenticateUser(String username, String password) {
		String sql = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);
			pstmt.setString(2, username);
			pstmt.setString(3, password);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToUser(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error authenticating user: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean usernameExists(String username) {
		String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			System.err.println("Error checking username existence: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean emailExists(String email) {
		String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			System.err.println("Error checking email existence: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Helper method to map ResultSet to User object
	 */
	private User mapResultSetToUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setEmail(rs.getString("email"));
		user.setPhone(rs.getString("phone"));
		user.setAddress(rs.getString("address"));
		user.setRole(rs.getString("role"));
		user.setCreatedAt(rs.getTimestamp("created_at"));
		user.setUpdatedAt(rs.getTimestamp("updated_at"));
		return user;
	}
}