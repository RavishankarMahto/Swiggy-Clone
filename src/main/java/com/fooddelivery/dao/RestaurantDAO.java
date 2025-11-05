package com.fooddelivery.dao;

import java.util.List;

import com.fooddelivery.model.Restaurant;

/**
 * Restaurant Data Access Object Interface Defines methods for
 * restaurant-related database operations
 */
public interface RestaurantDAO {

	/**
	 * Add a new restaurant
	 * 
	 * @param restaurant Restaurant object to add
	 * @return true if successful, false otherwise
	 */
	boolean addRestaurant(Restaurant restaurant);

	/**
	 * Get restaurant by ID
	 * 
	 * @param restaurantId Restaurant ID
	 * @return Restaurant object or null if not found
	 */
	Restaurant getRestaurantById(int restaurantId);

	/**
	 * Get all restaurants
	 * 
	 * @return List of all restaurants
	 */
	List<Restaurant> getAllRestaurants();

	/**
	 * Get active restaurants only
	 * 
	 * @return List of active restaurants
	 */
	List<Restaurant> getActiveRestaurants();

	/**
	 * Get restaurants by cuisine type
	 * 
	 * @param cuisineType Cuisine type to filter by
	 * @return List of restaurants with specified cuisine type
	 */
	List<Restaurant> getRestaurantsByCuisine(String cuisineType);

	/**
	 * Get restaurants by owner
	 * 
	 * @param ownerId Owner user ID
	 * @return List of restaurants owned by the user
	 */
	List<Restaurant> getRestaurantsByOwner(int ownerId);

	/**
	 * Search restaurants by name
	 * 
	 * @param searchTerm Search term
	 * @return List of restaurants matching the search term
	 */
	List<Restaurant> searchRestaurants(String searchTerm);

	/**
	 * Update restaurant information
	 * 
	 * @param restaurant Restaurant object with updated information
	 * @return true if successful, false otherwise
	 */
	boolean updateRestaurant(Restaurant restaurant);

	/**
	 * Delete restaurant by ID
	 * 
	 * @param restaurantId Restaurant ID to delete
	 * @return true if successful, false otherwise
	 */
	boolean deleteRestaurant(int restaurantId);

	/**
	 * Activate/Deactivate restaurant
	 * 
	 * @param restaurantId Restaurant ID
	 * @param isActive     Active status
	 * @return true if successful, false otherwise
	 */
	boolean setRestaurantStatus(int restaurantId, boolean isActive);

	/**
	 * Update restaurant rating
	 * 
	 * @param restaurantId Restaurant ID
	 * @param rating       New rating
	 * @return true if successful, false otherwise
	 */
	boolean updateRestaurantRating(int restaurantId, double rating);
}