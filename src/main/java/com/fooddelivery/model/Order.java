package com.fooddelivery.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Order Model Class Represents an order in the food delivery system
 */
public class Order {
	private int orderId;
	private int userId;
	private int restaurantId;
	private Timestamp orderDate;
	private BigDecimal totalAmount;
	private String deliveryAddress;
	private String status;
	private String paymentStatus;
	private BigDecimal deliveryFee;
	private String notes;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Additional fields for display
	private String userName;
	private String restaurantName;
	private List<OrderItem> orderItems;

	// Default constructor
	public Order() {
	}

	// Constructor with basic fields
	public Order(int userId, int restaurantId, BigDecimal totalAmount, String deliveryAddress) {
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.totalAmount = totalAmount;
		this.deliveryAddress = deliveryAddress;
		this.status = "PENDING";
		this.paymentStatus = "PENDING";
		this.deliveryFee = BigDecimal.ZERO;
	}

	// Getters and Setters
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "Order{" + "orderId=" + orderId + ", userId=" + userId + ", restaurantId=" + restaurantId
				+ ", totalAmount=" + totalAmount + ", status='" + status + '\'' + ", paymentStatus='" + paymentStatus
				+ '\'' + '}';
	}
}