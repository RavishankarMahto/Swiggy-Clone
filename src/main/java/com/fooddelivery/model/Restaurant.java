package com.fooddelivery.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Restaurant Model Class Represents a restaurant in the food delivery system
 */
public class Restaurant {
	private int restaurantId;
	private String name;
	private String description;
	private String address;
	private String phone;
	private String email;
	private String cuisineType;
	private BigDecimal rating;
	private BigDecimal deliveryFee;
	private int deliveryTime; // in minutes
	private boolean isActive;
	private int ownerId;
	private String imageUrl;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Default constructor
	public Restaurant() {
	}

	// Constructor with basic fields
	public Restaurant(String name, String address, String phone, String email, int ownerId) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.ownerId = ownerId;
		this.rating = BigDecimal.ZERO;
		this.deliveryFee = BigDecimal.ZERO;
		this.deliveryTime = 30;
		this.isActive = true;
	}

	// Getters and Setters
	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCuisineType() {
		return cuisineType;
	}

	public void setCuisineType(String cuisineType) {
		this.cuisineType = cuisineType;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Get average price for two people Computed value based on delivery fee or
	 * default
	 */
	public int getAveragePrice() {
		if (deliveryFee != null && deliveryFee.compareTo(BigDecimal.ZERO) > 0) {
			// Return a reasonable price based on delivery fee
			return deliveryFee.multiply(new BigDecimal("50")).intValue();
		}
		// Default average price for two
		return 300;
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

	@Override
	public String toString() {
		return "Restaurant{" + "restaurantId=" + restaurantId + ", name='" + name + '\'' + ", address='" + address
				+ '\'' + ", phone='" + phone + '\'' + ", cuisineType='" + cuisineType + '\'' + ", rating=" + rating
				+ ", isActive=" + isActive + '}';
	}
}
