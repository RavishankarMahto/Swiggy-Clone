package com.fooddelivery.dao;

import java.util.List;

import com.fooddelivery.model.OrderHistory;

/**
 * Order History Data Access Object Interface Defines methods for order
 * history-related database operations
 */
public interface OrderHistoryDAO {

	/**
	 * Add a new order history entry
	 * 
	 * @param orderHistory OrderHistory object to add
	 * @return true if successful, false otherwise
	 */
	boolean addOrderHistory(OrderHistory orderHistory);

	/**
	 * Get order history by ID
	 * 
	 * @param historyId History ID
	 * @return OrderHistory object or null if not found
	 */
	OrderHistory getOrderHistoryById(int historyId);

	/**
	 * Get all history entries for an order
	 * 
	 * @param orderId Order ID
	 * @return List of order history entries
	 */
	List<OrderHistory> getOrderHistoryByOrder(int orderId);

	/**
	 * Get order history by status
	 * 
	 * @param status Order status
	 * @return List of order history entries with specified status
	 */
	List<OrderHistory> getOrderHistoryByStatus(String status);

	/**
	 * Get recent order history (last N entries)
	 * 
	 * @param limit Number of recent entries to retrieve
	 * @return List of recent order history entries
	 */
	List<OrderHistory> getRecentOrderHistory(int limit);

	/**
	 * Get order history for a specific date range
	 * 
	 * @param startDate Start date
	 * @param endDate   End date
	 * @return List of order history entries within date range
	 */
	List<OrderHistory> getOrderHistoryByDateRange(java.sql.Timestamp startDate, java.sql.Timestamp endDate);

	/**
	 * Delete order history entry
	 * 
	 * @param historyId History ID to delete
	 * @return true if successful, false otherwise
	 */
	boolean deleteOrderHistory(int historyId);

	/**
	 * Delete all history entries for an order
	 * 
	 * @param orderId Order ID
	 * @return true if successful, false otherwise
	 */
	boolean deleteOrderHistoryByOrder(int orderId);

	/**
	 * Get count of history entries for an order
	 * 
	 * @param orderId Order ID
	 * @return Number of history entries
	 */
	int getOrderHistoryCount(int orderId);
}