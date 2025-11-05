package com.fooddelivery.daoimpl;

import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.util.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant DAO Implementation
 * Implements database operations for Restaurant entity
 */
public class RestaurantDAOImpl implements RestaurantDAO {
    
    @Override
    public boolean addRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO restaurants (name, description, address, phone, email, cuisine_type, rating, delivery_fee, delivery_time, is_active, owner_id, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getDescription());
            pstmt.setString(3, restaurant.getAddress());
            pstmt.setString(4, restaurant.getPhone());
            pstmt.setString(5, restaurant.getEmail());
            pstmt.setString(6, restaurant.getCuisineType());
            pstmt.setBigDecimal(7, restaurant.getRating());
            pstmt.setBigDecimal(8, restaurant.getDeliveryFee());
            pstmt.setInt(9, restaurant.getDeliveryTime());
            pstmt.setBoolean(10, restaurant.isActive());
            pstmt.setInt(11, restaurant.getOwnerId());
            pstmt.setString(12, restaurant.getImageUrl());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding restaurant: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Restaurant getRestaurantById(int restaurantId) {
        String sql = "SELECT * FROM restaurants WHERE restaurant_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToRestaurant(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting restaurant by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                restaurants.add(mapResultSetToRestaurant(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all restaurants: " + e.getMessage());
            e.printStackTrace();
        }
        
        return restaurants;
    }
    
    @Override
    public List<Restaurant> getActiveRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE is_active = true";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                restaurants.add(mapResultSetToRestaurant(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting active restaurants: " + e.getMessage());
            e.printStackTrace();
        }
        
        return restaurants;
    }
    
    @Override
    public List<Restaurant> getRestaurantsByCuisine(String cuisineType) {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE cuisine_type = ? AND is_active = true";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cuisineType);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                restaurants.add(mapResultSetToRestaurant(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting restaurants by cuisine: " + e.getMessage());
            e.printStackTrace();
        }
        
        return restaurants;
    }
    
    @Override
    public List<Restaurant> getRestaurantsByOwner(int ownerId) {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE owner_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                restaurants.add(mapResultSetToRestaurant(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting restaurants by owner: " + e.getMessage());
            e.printStackTrace();
        }
        
        return restaurants;
    }
    
    @Override
    public List<Restaurant> searchRestaurants(String searchTerm) {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE (name LIKE ? OR description LIKE ? OR cuisine_type LIKE ?) AND is_active = true";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                restaurants.add(mapResultSetToRestaurant(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching restaurants: " + e.getMessage());
            e.printStackTrace();
        }
        
        return restaurants;
    }
    
    @Override
    public boolean updateRestaurant(Restaurant restaurant) {
        String sql = "UPDATE restaurants SET name=?, description=?, address=?, phone=?, email=?, cuisine_type=?, rating=?, delivery_fee=?, delivery_time=?, is_active=?, image_url=? WHERE restaurant_id=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getDescription());
            pstmt.setString(3, restaurant.getAddress());
            pstmt.setString(4, restaurant.getPhone());
            pstmt.setString(5, restaurant.getEmail());
            pstmt.setString(6, restaurant.getCuisineType());
            pstmt.setBigDecimal(7, restaurant.getRating());
            pstmt.setBigDecimal(8, restaurant.getDeliveryFee());
            pstmt.setInt(9, restaurant.getDeliveryTime());
            pstmt.setBoolean(10, restaurant.isActive());
            pstmt.setString(11, restaurant.getImageUrl());
            pstmt.setInt(12, restaurant.getRestaurantId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating restaurant: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteRestaurant(int restaurantId) {
        String sql = "DELETE FROM restaurants WHERE restaurant_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, restaurantId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting restaurant: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean setRestaurantStatus(int restaurantId, boolean isActive) {
        String sql = "UPDATE restaurants SET is_active = ? WHERE restaurant_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, isActive);
            pstmt.setInt(2, restaurantId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error setting restaurant status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateRestaurantRating(int restaurantId, double rating) {
        String sql = "UPDATE restaurants SET rating = ? WHERE restaurant_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBigDecimal(1, BigDecimal.valueOf(rating));
            pstmt.setInt(2, restaurantId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating restaurant rating: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Helper method to map ResultSet to Restaurant object
     */
    private Restaurant mapResultSetToRestaurant(ResultSet rs) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurant_id"));
        restaurant.setName(rs.getString("name"));
        restaurant.setDescription(rs.getString("description"));
        restaurant.setAddress(rs.getString("address"));
        restaurant.setPhone(rs.getString("phone"));
        restaurant.setEmail(rs.getString("email"));
        restaurant.setCuisineType(rs.getString("cuisine_type"));
        restaurant.setRating(rs.getBigDecimal("rating"));
        restaurant.setDeliveryFee(rs.getBigDecimal("delivery_fee"));
        restaurant.setDeliveryTime(rs.getInt("delivery_time"));
        restaurant.setActive(rs.getBoolean("is_active"));
        restaurant.setOwnerId(rs.getInt("owner_id"));
        try { restaurant.setImageUrl(rs.getString("image_url")); } catch (SQLException ignore) {}
        restaurant.setCreatedAt(rs.getTimestamp("created_at"));
        restaurant.setUpdatedAt(rs.getTimestamp("updated_at"));
        return restaurant;
    }
}
