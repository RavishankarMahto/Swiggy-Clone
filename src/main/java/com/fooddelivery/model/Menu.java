package com.fooddelivery.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Menu Item Model Class Represents a menu item in a restaurant
 */
public class Menu {
	private int itemId;
	private int restaurantId;
	private String name;
	private String description;
	private BigDecimal price;
	private String category;
	private String imageUrl;
	private boolean isAvailable;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Default constructor
	public Menu() {
	}

	// Constructor with basic fields
	public Menu(int restaurantId, String name, String description, BigDecimal price, String category) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.isAvailable = true;
	}

	// Getters and Setters
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable = available;
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
		return "Menu{" + "itemId=" + itemId + ", restaurantId=" + restaurantId + ", name='" + name + '\'' + ", price="
				+ price + ", category='" + category + '\'' + ", isAvailable=" + isAvailable + '}';
	}
}