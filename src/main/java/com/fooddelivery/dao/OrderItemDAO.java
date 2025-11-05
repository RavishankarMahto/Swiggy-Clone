package com.fooddelivery.dao;

import java.util.List;

import com.fooddelivery.model.OrderItem;

/**
 * Order Item Data Access Object Interface Defines methods for order
 * item-related database operations
 */
public interface OrderItemDAO {

	/**
	 * Add an item to an order
	 * 
	 * @param orderItem Order item object to add
	 * @return true if successful, false otherwise
	 */
	boolean addOrderItem(OrderItem orderItem);

	/**
	 * Get order item by ID
	 * 
	 * @param orderItemId Order item ID
	 * @return Order item object or null if not found
	 */
	OrderItem getOrderItemById(int orderItemId);

	/**
	 * Get all items for an order
	 * 
	 * @param orderId Order ID
	 * @return List of order items for the order
	 */
	List<OrderItem> getOrderItemsByOrder(int orderId);

	/**
	 * Update order item quantity
	 * 
	 * @param orderItemId Order item ID
	 * @param quantity    New quantity
	 * @return true if successful, false otherwise
	 */
	boolean updateOrderItemQuantity(int orderItemId, int quantity);

	/**
	 * Update order item price
	 * 
	 * @param orderItemId Order item ID
	 * @param price       New price
	 * @return true if successful, false otherwise
	 */
	boolean updateOrderItemPrice(int orderItemId, double price);

	/**
	 * Remove an item from an order
	 * 
	 * @param orderItemId Order item ID to remove
	 * @return true if successful, false otherwise
	 */
	boolean removeOrderItem(int orderItemId);

	/**
	 * Remove all items from an order
	 * 
	 * @param orderId Order ID
	 * @return true if successful, false otherwise
	 */
	boolean removeAllOrderItems(int orderId);

	/**
	 * Get total quantity of items in an order
	 * 
	 * @param orderId Order ID
	 * @return Total quantity
	 */
	int getTotalQuantityByOrder(int orderId);

	/**
	 * Get total price of items in an order
	 * 
	 * @param orderId Order ID
	 * @return Total price
	 */
	double getTotalPriceByOrder(int orderId);
}