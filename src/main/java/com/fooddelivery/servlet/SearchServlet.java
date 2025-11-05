package com.fooddelivery.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.dao.MenuDAO;
import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.daoimpl.MenuDAOImpl;
import com.fooddelivery.daoimpl.RestaurantDAOImpl;
import com.fooddelivery.model.Menu;
import com.fooddelivery.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * SearchServlet - Handles advanced search functionality
 * Search restaurants, menu items, and cuisines
 */
//@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    
    private RestaurantDAO restaurantDAO;
    private MenuDAO menuDAO;
    
    @Override
    public void init() throws ServletException {
        restaurantDAO = new RestaurantDAOImpl();
        menuDAO = new MenuDAOImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String query = request.getParameter("q");
            String type = request.getParameter("type");
            String cuisine = request.getParameter("cuisine");
            String sortBy = request.getParameter("sortBy");
            String minPrice = request.getParameter("minPrice");
            String maxPrice = request.getParameter("maxPrice");
            
            List<Restaurant> restaurants = new ArrayList<>();
            List<Menu> menuItems = new ArrayList<>();
            
            if (query != null && !query.trim().isEmpty()) {
                if ("menu".equals(type)) {
                    // Search menu items
                    menuItems = searchMenuItems(query.trim());
                } else if ("restaurant".equals(type)) {
                    // Search restaurants
                    restaurants = searchRestaurants(query.trim());
                } else {
                    // Search both
                    restaurants = searchRestaurants(query.trim());
                    menuItems = searchMenuItems(query.trim());
                }
            } else if (cuisine != null && !cuisine.trim().isEmpty()) {
                // Filter by cuisine
                restaurants = restaurantDAO.getRestaurantsByCuisine(cuisine.trim());
            } else {
                // Show all active restaurants
                restaurants = restaurantDAO.getActiveRestaurants();
            }
            
            // Apply price filter to menu items
            if (minPrice != null || maxPrice != null) {
                menuItems = filterMenuItemsByPrice(menuItems, minPrice, maxPrice);
            }
            
            // Sort results
            restaurants = sortRestaurants(restaurants, sortBy);
            menuItems = sortMenuItems(menuItems, sortBy);
            
            request.setAttribute("restaurants", restaurants);
            request.setAttribute("menuItems", menuItems);
            request.setAttribute("query", query);
            request.setAttribute("type", type);
            request.setAttribute("cuisine", cuisine);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("pageTitle", "Search Results");
            
            request.getRequestDispatcher("/search-results.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in SearchServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while searching.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        doGet(request, response);
    }
    
    /**
     * Search restaurants by query
     */
    private List<Restaurant> searchRestaurants(String query) {
        try {
            return restaurantDAO.searchRestaurants(query);
        } catch (Exception e) {
            System.err.println("Error searching restaurants: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Search menu items by query
     */
    private List<Menu> searchMenuItems(String query) {
        try {
            // Get all restaurants first
            List<Restaurant> restaurants = restaurantDAO.getActiveRestaurants();
            List<Menu> allMenuItems = new ArrayList<>();
            
            // Search menu items in each restaurant
            for (Restaurant restaurant : restaurants) {
                List<Menu> restaurantMenuItems = menuDAO.searchMenuItems(restaurant.getRestaurantId(), query);
                allMenuItems.addAll(restaurantMenuItems);
            }
            
            return allMenuItems;
        } catch (Exception e) {
            System.err.println("Error searching menu items: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Filter menu items by price range
     */
    private List<Menu> filterMenuItemsByPrice(List<Menu> menuItems, String minPriceStr, String maxPriceStr) {
        List<Menu> filteredItems = new ArrayList<>();
        
        try {
            Double minPrice = minPriceStr != null && !minPriceStr.trim().isEmpty() ? 
                            Double.parseDouble(minPriceStr) : null;
            Double maxPrice = maxPriceStr != null && !maxPriceStr.trim().isEmpty() ? 
                            Double.parseDouble(maxPriceStr) : null;
            
            for (Menu item : menuItems) {
                double itemPrice = item.getPrice().doubleValue();
                
                boolean includeItem = true;
                
                if (minPrice != null && itemPrice < minPrice) {
                    includeItem = false;
                }
                
                if (maxPrice != null && itemPrice > maxPrice) {
                    includeItem = false;
                }
                
                if (includeItem) {
                    filteredItems.add(item);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing price filter: " + e.getMessage());
            return menuItems; // Return original list if price parsing fails
        }
        
        return filteredItems;
    }
    
    /**
     * Sort restaurants by criteria
     */
    private List<Restaurant> sortRestaurants(List<Restaurant> restaurants, String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return restaurants;
        }
        
        switch (sortBy.toLowerCase()) {
            case "name":
                restaurants.sort((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));
                break;
            case "rating":
                restaurants.sort((r1, r2) -> r2.getRating().compareTo(r1.getRating()));
                break;
            case "delivery_time":
                restaurants.sort((r1, r2) -> Integer.compare(r1.getDeliveryTime(), r2.getDeliveryTime()));
                break;
            case "delivery_fee":
                restaurants.sort((r1, r2) -> r1.getDeliveryFee().compareTo(r2.getDeliveryFee()));
                break;
            default:
                // No sorting or invalid sort criteria
                break;
        }
        
        return restaurants;
    }
    
    /**
     * Sort menu items by criteria
     */
    private List<Menu> sortMenuItems(List<Menu> menuItems, String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return menuItems;
        }
        
        switch (sortBy.toLowerCase()) {
            case "name":
                menuItems.sort((m1, m2) -> m1.getName().compareToIgnoreCase(m2.getName()));
                break;
            case "price_low":
                menuItems.sort((m1, m2) -> m1.getPrice().compareTo(m2.getPrice()));
                break;
            case "price_high":
                menuItems.sort((m1, m2) -> m2.getPrice().compareTo(m1.getPrice()));
                break;
            case "category":
                menuItems.sort((m1, m2) -> m1.getCategory().compareToIgnoreCase(m2.getCategory()));
                break;
            default:
                // No sorting or invalid sort criteria
                break;
        }
        
        return menuItems;
    }
}