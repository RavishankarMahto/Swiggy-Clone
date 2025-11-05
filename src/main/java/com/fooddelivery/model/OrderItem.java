package com.fooddelivery.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Order Item Model Class Represents an item within an order
 */
public class OrderItem {
	private int orderItemId;
	private int orderId;
	private int itemId;
	private int quantity;
	private BigDecimal price;
	private Timestamp createdAt;

	// Additional fields for display
	private String itemName;
	private String itemDescription;

	// Default constructor
	public OrderItem() {
	}

	// Constructor with basic fields
	public OrderItem(int orderId, int itemId, int quantity, BigDecimal price) {
		this.orderId = orderId;
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
	}

	// Getters and Setters
	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	// Calculate total price for this item
	public BigDecimal getTotalPrice() {
		return price.multiply(BigDecimal.valueOf(quantity));
	}

	@Override
	public String toString() {
		return "OrderItem{" + "orderItemId=" + orderItemId + ", orderId=" + orderId + ", itemId=" + itemId
				+ ", quantity=" + quantity + ", price=" + price + '}';
	}
}