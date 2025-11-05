package com.fooddelivery.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.dao.MenuDAO;
import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.dao.OrderItemDAO;
import com.fooddelivery.daoimpl.MenuDAOImpl;
import com.fooddelivery.daoimpl.OrderDAOImpl;
import com.fooddelivery.daoimpl.OrderItemDAOImpl;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * OrderServlet - Handles order management functionality
 * Create, view, and manage orders
 */
//@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private MenuDAO menuDAO;
    
    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAOImpl();
        orderItemDAO = new OrderItemDAOImpl();
        menuDAO = new MenuDAOImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            if ("view".equals(action)) {
                handleViewOrder(request, response);
            } else if ("history".equals(action)) {
                handleOrderHistory(request, response);
            } else if ("track".equals(action)) {
                handleTrackOrder(request, response);
            } else {
                handleDefault(request, response);
            }
        } catch (Exception e) {
            System.err.println("Error in OrderServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            if ("create".equals(action)) {
                handleCreateOrder(request, response);
            } else if ("cancel".equals(action)) {
                handleCancelOrder(request, response);
            } else if ("update".equals(action)) {
                handleUpdateOrder(request, response);
            } else {
                doGet(request, response);
            }
        } catch (Exception e) {
            System.err.println("Error in OrderServlet POST: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle default order page - show user's orders
     */
    private void handleDefault(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        User user = (User) request.getSession().getAttribute("user");
        List<Order> orders = orderDAO.getOrdersByUser(user.getUserId());
        
        request.setAttribute("orders", orders);
        request.setAttribute("pageTitle", "My Orders");
        
        request.getRequestDispatcher("/orders.jsp").forward(request, response);
    }
    
    /**
     * Handle view specific order
     */
    private void handleViewOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Order ID is required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        int orderId = Integer.parseInt(orderIdStr);
        User user = (User) request.getSession().getAttribute("user");
        
        Order order = orderDAO.getOrderById(orderId);
        
        if (order == null || order.getUserId() != user.getUserId()) {
            request.setAttribute("error", "Order not found or access denied.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrder(orderId);
        order.setOrderItems(orderItems);
        
        request.setAttribute("order", order);
        request.setAttribute("pageTitle", "Order Details");
        
        request.getRequestDispatcher("/order-details.jsp").forward(request, response);
    }
    
    /**
     * Handle order history
     */
    private void handleOrderHistory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        User user = (User) request.getSession().getAttribute("user");
        List<Order> orders = orderDAO.getOrdersByUser(user.getUserId());
        
        request.setAttribute("orders", orders);
        request.setAttribute("pageTitle", "Order History");
        
        request.getRequestDispatcher("/order-history.jsp").forward(request, response);
    }
    
    /**
     * Handle track order
     */
    private void handleTrackOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Order ID is required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        int orderId = Integer.parseInt(orderIdStr);
        User user = (User) request.getSession().getAttribute("user");
        
        Order order = orderDAO.getOrderById(orderId);
        
        if (order == null || order.getUserId() != user.getUserId()) {
            request.setAttribute("error", "Order not found or access denied.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        List<Order> orderHistory = orderDAO.getOrderHistory(orderId);
        
        request.setAttribute("order", order);
        request.setAttribute("orderHistory", orderHistory);
        request.setAttribute("pageTitle", "Track Order");
        
        request.getRequestDispatcher("/track-order.jsp").forward(request, response);
    }
    
    /**
     * Handle create order
     */
    private void handleCreateOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            User user = (User) request.getSession().getAttribute("user");
            
            String restaurantIdStr = request.getParameter("restaurantId");
            String deliveryAddress = request.getParameter("deliveryAddress");
            String notes = request.getParameter("notes");
            
            if (restaurantIdStr == null || deliveryAddress == null || 
                restaurantIdStr.trim().isEmpty() || deliveryAddress.trim().isEmpty()) {
                
                request.setAttribute("error", "Restaurant ID and delivery address are required.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            int restaurantId = Integer.parseInt(restaurantIdStr);
            
            // Get cart items from session or request parameters
            List<OrderItem> cartItems = getCartItems(request);
            
            if (cartItems.isEmpty()) {
                request.setAttribute("error", "Cart is empty. Please add items to your cart.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            // Calculate total amount
            BigDecimal totalAmount = calculateTotalAmount(cartItems);
            
            // Create order
            Order order = new Order(user.getUserId(), restaurantId, totalAmount, deliveryAddress);
            order.setNotes(notes);
            
            int orderId = orderDAO.createOrder(order);
            
            if (orderId > 0) {
                // Add order items
                for (OrderItem item : cartItems) {
                    item.setOrderId(orderId);
                    orderItemDAO.addOrderItem(item);
                }
                
                // Clear cart
                request.getSession().removeAttribute("cart");
                
                request.setAttribute("success", "Order placed successfully! Order ID: " + orderId);
                response.sendRedirect(request.getContextPath() + "/order?action=view&orderId=" + orderId);
            } else {
                request.setAttribute("error", "Failed to create order. Please try again.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error creating order: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to create order. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle cancel order
     */
    private void handleCancelOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Order ID is required.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        int orderId = Integer.parseInt(orderIdStr);
        User user = (User) request.getSession().getAttribute("user");
        
        Order order = orderDAO.getOrderById(orderId);
        
        if (order == null || order.getUserId() != user.getUserId()) {
            request.setAttribute("error", "Order not found or access denied.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        
        boolean success = orderDAO.cancelOrder(orderId);
        
        if (success) {
            request.setAttribute("success", "Order cancelled successfully.");
            response.sendRedirect(request.getContextPath() + "/order?action=view&orderId=" + orderId);
        } else {
            request.setAttribute("error", "Failed to cancel order. It may have already been processed.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle update order
     */
    private void handleUpdateOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // This would handle order updates (like changing delivery address)
        // Implementation depends on business requirements
        request.setAttribute("error", "Order update functionality not implemented yet.");
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
    
    /**
     * Get cart items from session or request
     */
    @SuppressWarnings("unchecked")
    private List<OrderItem> getCartItems(HttpServletRequest request) {
        List<OrderItem> cartItems = (List<OrderItem>) request.getSession().getAttribute("cart");
        return cartItems != null ? cartItems : new ArrayList<>();
    }
    
    /**
     * Calculate total amount for cart items
     */
    private BigDecimal calculateTotalAmount(List<OrderItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : cartItems) {
            total = total.add(item.getTotalPrice());
        }
		return total;
	}
}