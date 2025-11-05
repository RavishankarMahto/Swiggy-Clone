package com.fooddelivery.dao;

import java.util.List;

import com.fooddelivery.model.Menu;

/**
 * Menu Data Access Object Interface Defines methods for menu item-related
 * database operations
 */
public interface MenuDAO {

	/**
	 * Add a new menu item
	 * 
	 * @param menuItem Menu item object to add
	 * @return true if successful, false otherwise
	 */
	boolean addMenuItem(Menu menuItem);

	/**
	 * Get menu item by ID
	 * 
	 * @param itemId Menu item ID
	 * @return Menu item object or null if not found
	 */
	Menu getMenuItemById(int itemId);

	/**
	 * Get all menu items for a restaurant
	 * 
	 * @param restaurantId Restaurant ID
	 * @return List of menu items for the restaurant
	 */
	List<Menu> getMenuItemsByRestaurant(int restaurantId);

	/**
	 * Get available menu items for a restaurant
	 * 
	 * @param restaurantId Restaurant ID
	 * @return List of available menu items
	 */
	List<Menu> getAvailableMenuItemsByRestaurant(int restaurantId);

	/**
	 * Get menu items by category
	 * 
	 * @param restaurantId Restaurant ID
	 * @param category     Category name
	 * @return List of menu items in the category
	 */
	List<Menu> getMenuItemsByCategory(int restaurantId, String category);

	/**
	 * Search menu items by name
	 * 
	 * @param restaurantId Restaurant ID
	 * @param searchTerm   Search term
	 * @return List of menu items matching the search term
	 */
	List<Menu> searchMenuItems(int restaurantId, String searchTerm);

	/**
	 * Update menu item information
	 * 
	 * @param menuItem Menu item object with updated information
	 * @return true if successful, false otherwise
	 */
	boolean updateMenuItem(Menu menuItem);

	/**
	 * Delete menu item by ID
	 * 
	 * @param itemId Menu item ID to delete
	 * @return true if successful, false otherwise
	 */
	boolean deleteMenuItem(int itemId);

	/**
	 * Set menu item availability
	 * 
	 * @param itemId      Menu item ID
	 * @param isAvailable Availability status
	 * @return true if successful, false otherwise
	 */
	boolean setMenuItemAvailability(int itemId, boolean isAvailable);

	/**
	 * Get all categories for a restaurant
	 * 
	 * @param restaurantId Restaurant ID
	 * @return List of unique categories
	 */
	List<String> getCategoriesByRestaurant(int restaurantId);
}