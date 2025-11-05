package com.fooddelivery.dao;

import java.util.List;

import com.fooddelivery.model.Order;

/**
 * Order Data Access Object Interface Defines methods for order-related database
 * operations
 */
public interface OrderDAO {

	/**
	 * Create a new order
	 * 
	 * @param order Order object to create
	 * @return Order ID if successful, -1 otherwise
	 */
	int createOrder(Order order);

	/**
	 * Get order by ID
	 * 
	 * @param orderId Order ID
	 * @return Order object or null if not found
	 */
	Order getOrderById(int orderId);

	/**
	 * Get all orders for a user
	 * 
	 * @param userId User ID
	 * @return List of orders for the user
	 */
	List<Order> getOrdersByUser(int userId);

	/**
	 * Get all orders for a restaurant
	 * 
	 * @param restaurantId Restaurant ID
	 * @return List of orders for the restaurant
	 */
	List<Order> getOrdersByRestaurant(int restaurantId);

	/**
	 * Get orders by status
	 * 
	 * @param status Order status
	 * @return List of orders with specified status
	 */
	List<Order> getOrdersByStatus(String status);

	/**
	 * Get orders by payment status
	 * 
	 * @param paymentStatus Payment status
	 * @return List of orders with specified payment status
	 */
	List<Order> getOrdersByPaymentStatus(String paymentStatus);

	/**
	 * Update order status
	 * 
	 * @param orderId Order ID
	 * @param status  New status
	 * @return true if successful, false otherwise
	 */
	boolean updateOrderStatus(int orderId, String status);

	/**
	 * Update payment status
	 * 
	 * @param orderId       Order ID
	 * @param paymentStatus New payment status
	 * @return true if successful, false otherwise
	 */
	boolean updatePaymentStatus(int orderId, String paymentStatus);

	/**
	 * Update order information
	 * 
	 * @param order Order object with updated information
	 * @return true if successful, false otherwise
	 */
	boolean updateOrder(Order order);

	/**
	 * Cancel an order
	 * 
	 * @param orderId Order ID to cancel
	 * @return true if successful, false otherwise
	 */
	boolean cancelOrder(int orderId);

	/**
	 * Get order history for tracking
	 * 
	 * @param orderId Order ID
	 * @return List of order history entries
	 */
	List<Order> getOrderHistory(int orderId);

	/**
	 * Get total sales for a restaurant
	 * 
	 * @param restaurantId Restaurant ID
	 * @return Total sales amount
	 */
	double getTotalSalesByRestaurant(int restaurantId);

	/**
	 * Get order count by status
	 * 
	 * @param status Order status
	 * @return Number of orders with specified status
	 */
	int getOrderCountByStatus(String status);
}