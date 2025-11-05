package com.fooddelivery.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fooddelivery.dao.MenuDAO;
import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.daoimpl.MenuDAOImpl;
import com.fooddelivery.daoimpl.RestaurantDAOImpl;
import com.fooddelivery.model.Menu;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * MenuServlet - Handles menu operations
 * View, add, update, delete menu items
 */
//@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    
    private MenuDAO menuDAO;
    private RestaurantDAO restaurantDAO;
    
    @Override
    public void init() throws ServletException {
        menuDAO = new MenuDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if (action == null || action.isEmpty()) {
                // Default: Show menu for a restaurant
                handleShowMenu(request, response);
            } else {
                switch (action) {
                    case "view":
                        handleViewMenuItem(request, response);
                        break;
                    case "add":
                        handleShowAddMenu(request, response);
                        break;
                    case "edit":
                        handleShowEditMenu(request, response);
                        break;
                    case "delete":
                        handleDeleteMenuItem(request, response);
                        break;
                    case "search":
                        handleSearchMenu(request, response);
                        break;
                    case "toggle":
                        handleToggleAvailability(request, response);
                        break;
                    default:
                        handleShowMenu(request, response);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in MenuServlet GET: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if (action != null) {
                switch (action) {
                    case "add":
                        handleAddMenuItem(request, response);
                        break;
                    case "update":
                        handleUpdateMenuItem(request, response);
                        break;
                    case "delete":
                        handleDeleteMenuItem(request, response);
                        break;
                    default:
                        handleShowMenu(request, response);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in MenuServlet POST: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Show menu for a restaurant
     */
    private void handleShowMenu(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String restaurantIdStr = request.getParameter("restaurantId");
        String category = request.getParameter("category");
        
        if (restaurantIdStr == null || restaurantIdStr.isEmpty()) {
            request.setAttribute("error", "Restaurant ID is required");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        try {
            int restaurantId = Integer.parseInt(restaurantIdStr);
            
            // Get restaurant information
            Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
            if (restaurant == null) {
                request.setAttribute("error", "Restaurant not found");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            // Get menu items
            List<Menu> menuItems;
            if (category != null && !category.isEmpty()) {
                menuItems = menuDAO.getMenuItemsByCategory(restaurantId, category);
            } else {
                menuItems = menuDAO.getAvailableMenuItemsByRestaurant(restaurantId);
            }
            
            // Get categories
            List<String> categories = menuDAO.getCategoriesByRestaurant(restaurantId);
            
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("menuItems", menuItems);
            request.setAttribute("categories", categories);
            request.setAttribute("selectedCategory", category);
            
            request.getRequestDispatcher("/menu.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid restaurant ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * View a single menu item
     */
    private void handleViewMenuItem(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String itemIdStr = request.getParameter("itemId");
        
        if (itemIdStr == null || itemIdStr.isEmpty()) {
            request.setAttribute("error", "Menu item ID is required");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        try {
            int itemId = Integer.parseInt(itemIdStr);
            Menu menuItem = menuDAO.getMenuItemById(itemId);
            
            if (menuItem == null) {
                request.setAttribute("error", "Menu item not found");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("menuItem", menuItem);
            request.getRequestDispatcher("/menu-detail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid menu item ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Show add menu item form
     */
    private void handleShowAddMenu(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        
        if (user == null || (!user.getRole().equals("restaurant_owner") && !user.getRole().equals("admin"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String restaurantIdStr = request.getParameter("restaurantId");
        if (restaurantIdStr != null && !restaurantIdStr.isEmpty()) {
            try {
                int restaurantId = Integer.parseInt(restaurantIdStr);
                Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
                request.setAttribute("restaurant", restaurant);
            } catch (NumberFormatException e) {
                // Invalid restaurant ID
            }
        }
        
        request.getRequestDispatcher("/add-menu.jsp").forward(request, response);
    }
    
    /**
     * Show edit menu item form
     */
    private void handleShowEditMenu(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        
        if (user == null || (!user.getRole().equals("restaurant_owner") && !user.getRole().equals("admin"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String itemIdStr = request.getParameter("itemId");
        
        if (itemIdStr == null || itemIdStr.isEmpty()) {
            request.setAttribute("error", "Menu item ID is required");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        try {
            int itemId = Integer.parseInt(itemIdStr);
            Menu menuItem = menuDAO.getMenuItemById(itemId);
            
            if (menuItem == null) {
                request.setAttribute("error", "Menu item not found");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("menuItem", menuItem);
            request.getRequestDispatcher("/edit-menu.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid menu item ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Add a new menu item
     */
    private void handleAddMenuItem(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        
        if (user == null || (!user.getRole().equals("restaurant_owner") && !user.getRole().equals("admin"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            String category = request.getParameter("category");
            String imageUrl = request.getParameter("imageUrl");
            
            Menu menuItem = new Menu();
            menuItem.setRestaurantId(restaurantId);
            menuItem.setName(name);
            menuItem.setDescription(description);
            menuItem.setPrice(price);
            menuItem.setCategory(category);
            menuItem.setImageUrl(imageUrl);
            menuItem.setAvailable(true);
            
            boolean success = menuDAO.addMenuItem(menuItem);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/menu?restaurantId=" + restaurantId + "&message=Menu item added successfully");
            } else {
                request.setAttribute("error", "Failed to add menu item");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error adding menu item: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Update a menu item
     */
    private void handleUpdateMenuItem(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        
        if (user == null || (!user.getRole().equals("restaurant_owner") && !user.getRole().equals("admin"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            String category = request.getParameter("category");
            String imageUrl = request.getParameter("imageUrl");
            
            Menu menuItem = menuDAO.getMenuItemById(itemId);
            if (menuItem == null) {
                request.setAttribute("error", "Menu item not found");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            menuItem.setName(name);
            menuItem.setDescription(description);
            menuItem.setPrice(price);
            menuItem.setCategory(category);
            menuItem.setImageUrl(imageUrl);
            
            boolean success = menuDAO.updateMenuItem(menuItem);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/menu?restaurantId=" + menuItem.getRestaurantId() + "&message=Menu item updated successfully");
            } else {
                request.setAttribute("error", "Failed to update menu item");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error updating menu item: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Delete a menu item
     */
    private void handleDeleteMenuItem(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        
        if (user == null || (!user.getRole().equals("restaurant_owner") && !user.getRole().equals("admin"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String itemIdStr = request.getParameter("itemId");
        
        if (itemIdStr == null || itemIdStr.isEmpty()) {
            request.setAttribute("error", "Menu item ID is required");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        try {
            int itemId = Integer.parseInt(itemIdStr);
            Menu menuItem = menuDAO.getMenuItemById(itemId);
            
            if (menuItem == null) {
                request.setAttribute("error", "Menu item not found");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            int restaurantId = menuItem.getRestaurantId();
            boolean success = menuDAO.deleteMenuItem(itemId);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/menu?restaurantId=" + restaurantId + "&message=Menu item deleted successfully");
            } else {
                request.setAttribute("error", "Failed to delete menu item");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid menu item ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Search menu items
     */
    private void handleSearchMenu(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String restaurantIdStr = request.getParameter("restaurantId");
        String searchTerm = request.getParameter("searchTerm");
        
        if (restaurantIdStr == null || restaurantIdStr.isEmpty()) {
            request.setAttribute("error", "Restaurant ID is required");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        if (searchTerm == null || searchTerm.isEmpty()) {
            request.setAttribute("error", "Search term is required");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        try {
            int restaurantId = Integer.parseInt(restaurantIdStr);
            List<Menu> menuItems = menuDAO.searchMenuItems(restaurantId, searchTerm);
            
            Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
            
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("menuItems", menuItems);
            request.setAttribute("searchTerm", searchTerm);
            
            request.getRequestDispatcher("/menu.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid restaurant ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Toggle menu item availability
     */
    private void handleToggleAvailability(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);
        
        if (user == null || (!user.getRole().equals("restaurant_owner") && !user.getRole().equals("admin"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String itemIdStr = request.getParameter("itemId");
        String available = request.getParameter("available");
        
        if (itemIdStr == null || itemIdStr.isEmpty()) {
            request.setAttribute("error", "Menu item ID is required");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        try {
            int itemId = Integer.parseInt(itemIdStr);
            boolean isAvailable = "true".equalsIgnoreCase(available);
            
            boolean success = menuDAO.setMenuItemAvailability(itemId, isAvailable);
            
            Menu menuItem = menuDAO.getMenuItemById(itemId);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/menu?restaurantId=" + menuItem.getRestaurantId() + "&message=Availability updated successfully");
            } else {
                request.setAttribute("error", "Failed to update availability");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid menu item ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}