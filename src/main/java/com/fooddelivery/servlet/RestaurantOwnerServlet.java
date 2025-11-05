package com.fooddelivery.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fooddelivery.dao.MenuDAO;
import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.daoimpl.MenuDAOImpl;
import com.fooddelivery.daoimpl.OrderDAOImpl;
import com.fooddelivery.daoimpl.RestaurantDAOImpl;
import com.fooddelivery.model.Menu;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * RestaurantOwnerServlet - Handles restaurant owner functionality
 * Manage restaurant, menu items, and orders
 */
//@WebServlet("/restaurant-owner/*")
public class RestaurantOwnerServlet extends HttpServlet {
    
    private RestaurantDAO restaurantDAO;
    private MenuDAO menuDAO;
    private OrderDAO orderDAO;
    
    @Override
    public void init() throws ServletException {
        restaurantDAO = new RestaurantDAOImpl();
        menuDAO = new MenuDAOImpl();
        orderDAO = new OrderDAOImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!isRestaurantOwner(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || "/".equals(pathInfo) || "/dashboard".equals(pathInfo)) {
                handleDashboard(request, response);
            } else if ("/restaurant".equals(pathInfo)) {
                handleRestaurantDetails(request, response);
            } else if ("/menu".equals(pathInfo)) {
                handleMenuManagement(request, response);
            } else if ("/orders".equals(pathInfo)) {
                handleOrders(request, response);
            } else if ("/edit-menu".equals(pathInfo)) {
                handleEditMenu(request, response);
            } else {
                request.setAttribute("error", "Page not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.err.println("Error in RestaurantOwnerServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!isRestaurantOwner(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        String action = request.getParameter("action");
        
        try {
            if ("/restaurant".equals(pathInfo)) {
                if ("update".equals(action)) {
                    handleUpdateRestaurant(request, response);
                }
            } else if ("/menu".equals(pathInfo)) {
                if ("add".equals(action)) {
                    handleAddMenuItem(request, response);
                } else if ("update".equals(action)) {
                    handleUpdateMenuItem(request, response);
                } else if ("delete".equals(action)) {
                    handleDeleteMenuItem(request, response);
                } else if ("toggle".equals(action)) {
                    handleToggleMenuItem(request, response);
                }
            } else if ("/orders".equals(pathInfo)) {
                if ("update".equals(action)) {
                    handleUpdateOrder(request, response);
                }
            } else {
                doGet(request, response);
            }
        } catch (Exception e) {
            System.err.println("Error in RestaurantOwnerServlet POST: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    private void handleUpdateRestaurant(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	/**
     * Check if current user is restaurant owner
     */
    private boolean isRestaurantOwner(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        
        User user = (User) session.getAttribute("user");
        return user != null && "RESTAURANT_OWNER".equals(user.getRole());
    }
    
    /**
     * Get restaurant owned by current user
     */
    private Restaurant getOwnedRestaurant(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Restaurant> restaurants = restaurantDAO.getRestaurantsByOwner(user.getUserId());
        return restaurants.isEmpty() ? null : restaurants.get(0);
    }
    
    /**
     * Handle restaurant owner dashboard
     */
    private void handleDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Restaurant restaurant = getOwnedRestaurant(request);
            if (restaurant == null) {
                request.setAttribute("error", "No restaurant found for your account.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            // Get restaurant statistics
            int totalMenuItems = menuDAO.getMenuItemsByRestaurant(restaurant.getRestaurantId()).size();
            int availableMenuItems = menuDAO.getAvailableMenuItemsByRestaurant(restaurant.getRestaurantId()).size();
            int pendingOrders = orderDAO.getOrdersByRestaurant(restaurant.getRestaurantId()).size();
            double totalSales = orderDAO.getTotalSalesByRestaurant(restaurant.getRestaurantId());
            
            // Get recent orders
            List<Order> recentOrders = orderDAO.getOrdersByRestaurant(restaurant.getRestaurantId());
            
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("totalMenuItems", totalMenuItems);
            request.setAttribute("availableMenuItems", availableMenuItems);
            request.setAttribute("pendingOrders", pendingOrders);
            request.setAttribute("totalSales", totalSales);
            request.setAttribute("recentOrders", recentOrders);
            request.setAttribute("pageTitle", "Restaurant Dashboard");
            
            request.getRequestDispatcher("/restaurant-owner/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error loading restaurant dashboard: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to load dashboard data.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle restaurant details
     */
    private void handleRestaurantDetails(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Restaurant restaurant = getOwnedRestaurant(request);
            if (restaurant == null) {
                request.setAttribute("error", "No restaurant found for your account.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("pageTitle", "Restaurant Details");
            
            request.getRequestDispatcher("/restaurant-owner/restaurant-details.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error loading restaurant details: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to load restaurant details.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle menu management
     */
    private void handleMenuManagement(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Restaurant restaurant = getOwnedRestaurant(request);
            if (restaurant == null) {
                request.setAttribute("error", "No restaurant found for your account.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            List<Menu> menuItems = menuDAO.getMenuItemsByRestaurant(restaurant.getRestaurantId());
            List<String> categories = menuDAO.getCategoriesByRestaurant(restaurant.getRestaurantId());
            
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("menuItems", menuItems);
            request.setAttribute("categories", categories);
            request.setAttribute("pageTitle", "Menu Management");
            
            request.getRequestDispatcher("/restaurant-owner/menu-management.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error loading menu management: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to load menu items.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle orders management
     */
    private void handleOrders(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Restaurant restaurant = getOwnedRestaurant(request);
            if (restaurant == null) {
                request.setAttribute("error", "No restaurant found for your account.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            String status = request.getParameter("status");
            List<Order> orders;
            
            if (status != null && !status.trim().isEmpty()) {
                orders = orderDAO.getOrdersByStatus(status);
                // Filter orders for this restaurant
                orders.removeIf(order -> order.getRestaurantId() != restaurant.getRestaurantId());
                request.setAttribute("selectedStatus", status);
            } else {
                orders = orderDAO.getOrdersByRestaurant(restaurant.getRestaurantId());
                request.setAttribute("selectedStatus", "ALL");
            }
            
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("orders", orders);
            request.setAttribute("pageTitle", "Order Management");
            
            request.getRequestDispatcher("/restaurant-owner/orders.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error loading orders: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to load orders.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle edit menu item
     */
    private void handleEditMenu(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String itemIdStr = request.getParameter("itemId");
            if (itemIdStr == null) {
                request.setAttribute("error", "Menu item ID is required.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            int itemId = Integer.parseInt(itemIdStr);
            Menu menuItem = menuDAO.getMenuItemById(itemId);
            
            if (menuItem == null) {
                request.setAttribute("error", "Menu item not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            // Verify ownership
            Restaurant restaurant = getOwnedRestaurant(request);
            if (restaurant == null || menuItem.getRestaurantId() != restaurant.getRestaurantId()) {
                request.setAttribute("error", "Access denied.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("menuItem", menuItem);
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("pageTitle", "Edit Menu Item");
            
            request.getRequestDispatcher("/restaurant-owner/edit-menu-item.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error loading edit menu: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to load menu item.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle add menu item
     */
    private void handleAddMenuItem(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Restaurant restaurant = getOwnedRestaurant(request);
            if (restaurant == null) {
                request.setAttribute("error", "No restaurant found for your account.");
                response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
                return;
            }
            
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String category = request.getParameter("category");
            String imageUrl = request.getParameter("imageUrl");
            
            if (name == null || name.trim().isEmpty() || 
                description == null || priceStr == null || category == null) {
                
                request.setAttribute("error", "All fields are required.");
                response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
                return;
            }
            
            BigDecimal price = new BigDecimal(priceStr);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("error", "Price must be greater than 0.");
                response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
                return;
            }
            
            Menu menuItem = new Menu(restaurant.getRestaurantId(), name, description, price, category);
            menuItem.setImageUrl(imageUrl);
            
            boolean success = menuDAO.addMenuItem(menuItem);
            
            if (success) {
                request.setAttribute("success", "Menu item added successfully.");
            } else {
                request.setAttribute("error", "Failed to add menu item.");
            }
            
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
            
        } catch (Exception e) {
            System.err.println("Error adding menu item: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to add menu item.");
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
        }
    }
    
    /**
     * Handle update menu item
     */
    private void handleUpdateMenuItem(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String itemIdStr = request.getParameter("itemId");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String category = request.getParameter("category");
            String imageUrl = request.getParameter("imageUrl");
            
            if (itemIdStr == null || name == null || name.trim().isEmpty() || 
                description == null || priceStr == null || category == null) {
                
                request.setAttribute("error", "All fields are required.");
                response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
                return;
            }
            
            int itemId = Integer.parseInt(itemIdStr);
            BigDecimal price = new BigDecimal(priceStr);
            
            Menu menuItem = menuDAO.getMenuItemById(itemId);
            if (menuItem == null) {
                request.setAttribute("error", "Menu item not found.");
                response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
                return;
            }
            
            // Update menu item
            menuItem.setName(name);
            menuItem.setDescription(description);
            menuItem.setPrice(price);
            menuItem.setCategory(category);
            menuItem.setImageUrl(imageUrl);
            
            boolean success = menuDAO.updateMenuItem(menuItem);
            
            if (success) {
                request.setAttribute("success", "Menu item updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update menu item.");
            }
            
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
            
        } catch (Exception e) {
            System.err.println("Error updating menu item: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to update menu item.");
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
        }
    }
    
    /**
     * Handle delete menu item
     */
    private void handleDeleteMenuItem(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String itemIdStr = request.getParameter("itemId");
            int itemId = Integer.parseInt(itemIdStr);
            
            boolean success = menuDAO.deleteMenuItem(itemId);
            
            if (success) {
                request.setAttribute("success", "Menu item deleted successfully.");
            } else {
                request.setAttribute("error", "Failed to delete menu item.");
            }
            
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
            
        } catch (Exception e) {
            System.err.println("Error deleting menu item: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to delete menu item.");
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
        }
    }
    
    /**
     * Handle toggle menu item availability
     */
    private void handleToggleMenuItem(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String itemIdStr = request.getParameter("itemId");
            String availableStr = request.getParameter("available");
            
            int itemId = Integer.parseInt(itemIdStr);
            boolean available = "true".equals(availableStr);
            
            boolean success = menuDAO.setMenuItemAvailability(itemId, available);
            
            if (success) {
                request.setAttribute("success", "Menu item availability updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update menu item availability.");
            }
            
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
            
        } catch (Exception e) {
            System.err.println("Error toggling menu item: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to update menu item availability.");
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/menu");
        }
    }
    
    /**
     * Handle update order status
     */
    private void handleUpdateOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String orderIdStr = request.getParameter("orderId");
            String status = request.getParameter("status");
            
            if (orderIdStr == null || status == null) {
                request.setAttribute("error", "Order ID and status are required.");
                response.sendRedirect(request.getContextPath() + "/restaurant-owner/orders");
                return;
            }
            
            int orderId = Integer.parseInt(orderIdStr);
            
            boolean success = orderDAO.updateOrderStatus(orderId, status);
            
            if (success) {
                request.setAttribute("success", "Order status updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update order status.");
            }
            
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/orders");
            
        } catch (Exception e) {
            System.err.println("Error updating order: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to update order status.");
            response.sendRedirect(request.getContextPath() + "/restaurant-owner/orders");
        }
    }
}