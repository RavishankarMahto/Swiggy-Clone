package com.fooddelivery.dao;

import java.util.List;

import com.fooddelivery.model.User;

/**
 * User Data Access Object Interface Defines methods for user-related database
 * operations
 */
public interface UserDAO {

	/**
	 * Add a new user to the database
	 * 
	 * @param user User object to add
	 * @return true if successful, false otherwise
	 */
	boolean addUser(User user);

	/**
	 * Get a user by ID
	 * 
	 * @param userId User ID
	 * @return User object or null if not found
	 */
	User getUserById(int userId);

	/**
	 * Get a user by username
	 * 
	 * @param username Username
	 * @return User object or null if not found
	 */
	User getUserByUsername(String username);

	/**
	 * Get a user by email
	 * 
	 * @param email Email address
	 * @return User object or null if not found
	 */
	User getUserByEmail(String email);

	/**
	 * Update user information
	 * 
	 * @param user User object with updated information
	 * @return true if successful, false otherwise
	 */
	boolean updateUser(User user);

	/**
	 * Delete a user by ID
	 * 
	 * @param userId User ID to delete
	 * @return true if successful, false otherwise
	 */
	boolean deleteUser(int userId);

	/**
	 * Get all users
	 * 
	 * @return List of all users
	 */
	List<User> getAllUsers();

	/**
	 * Get users by role
	 * 
	 * @param role User role (CUSTOMER, RESTAURANT_OWNER, ADMIN)
	 * @return List of users with specified role
	 */
	List<User> getUsersByRole(String role);

	/**
	 * Authenticate user login
	 * 
	 * @param username Username or email
	 * @param password Password
	 * @return User object if authentication successful, null otherwise
	 */
	User authenticateUser(String username, String password);

	/**
	 * Check if username exists
	 * 
	 * @param username Username to check
	 * @return true if exists, false otherwise
	 */
	boolean usernameExists(String username);

	/**
	 * Check if email exists
	 * 
	 * @param email Email to check
	 * @return true if exists, false otherwise
	 */
	boolean emailExists(String email);
}