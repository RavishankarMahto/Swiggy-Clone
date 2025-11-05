package com.fooddelivery.model;

import java.sql.Timestamp;

/**
 * Order History Model Class Represents the history of order status changes
 */
public class OrderHistory {
	private int historyId;
	private int orderId;
	private String status;
	private String notes;
	private Timestamp createdAt;

	// Default constructor
	public OrderHistory() {
	}

	// Constructor with basic fields
	public OrderHistory(int orderId, String status, String notes) {
		this.orderId = orderId;
		this.status = status;
		this.notes = notes;
	}

	// Getters and Setters
	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "OrderHistory{" + "historyId=" + historyId + ", orderId=" + orderId + ", status='" + status + '\''
				+ ", notes='" + notes + '\'' + ", createdAt=" + createdAt + '}';
	}
}