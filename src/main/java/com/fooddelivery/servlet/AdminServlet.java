package com.fooddelivery.servlet;

import java.io.IOException;
import java.util.List;

import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.dao.UserDAO;
import com.fooddelivery.daoimpl.OrderDAOImpl;
import com.fooddelivery.daoimpl.RestaurantDAOImpl;
import com.fooddelivery.daoimpl.UserDAOImpl;
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
 * AdminServlet - Handles admin dashboard and management functionality
 * Admin-only operations for managing the system
 */
@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

	private UserDAO userDAO;
	private RestaurantDAO restaurantDAO;
	private OrderDAO orderDAO;

	@Override
	public void init() throws ServletException {
		userDAO = new UserDAOImpl();
		restaurantDAO = new RestaurantDAOImpl();
		orderDAO = new OrderDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isAdmin(request)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String pathInfo = request.getPathInfo();

		try {
			if (pathInfo == null || "/".equals(pathInfo) || "/dashboard".equals(pathInfo)) {
				handleDashboard(request, response);
			} else if ("/users".equals(pathInfo)) {
				handleUsers(request, response);
			} else if ("/restaurants".equals(pathInfo)) {
				handleRestaurants(request, response);
			} else if ("/orders".equals(pathInfo)) {
				handleOrders(request, response);
			} else if ("/statistics".equals(pathInfo)) {
				handleStatistics(request, response);
			} else {
				request.setAttribute("error", "Page not found.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			System.err.println("Error in AdminServlet: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while processing your request.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isAdmin(request)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String pathInfo = request.getPathInfo();
		String action = request.getParameter("action");

		try {
			if ("/restaurants".equals(pathInfo)) {
				if ("activate".equals(action)) {
					handleActivateRestaurant(request, response);
				} else if ("deactivate".equals(action)) {
					handleDeactivateRestaurant(request, response);
				}
			} else if ("/users".equals(pathInfo)) {
				if ("delete".equals(action)) {
					handleDeleteUser(request, response);
				}
			} else {
				doGet(request, response);
			}
		} catch (Exception e) {
			System.err.println("Error in AdminServlet POST: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while processing your request.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Check if current user is admin
	 */
	private boolean isAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return false;

		User user = (User) session.getAttribute("user");
		return user != null && "ADMIN".equals(user.getRole());
	}

	/**
	 * Handle admin dashboard
	 */
	private void handleDashboard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Get statistics
			int totalUsers = userDAO.getAllUsers().size();
			int totalRestaurants = restaurantDAO.getAllRestaurants().size();
			int activeRestaurants = restaurantDAO.getActiveRestaurants().size();
			int pendingOrders = orderDAO.getOrderCountByStatus("PENDING");
			int totalOrders = orderDAO.getOrderCountByStatus("PENDING") + orderDAO.getOrderCountByStatus("CONFIRMED")
					+ orderDAO.getOrderCountByStatus("PREPARING") + orderDAO.getOrderCountByStatus("OUT_FOR_DELIVERY")
					+ orderDAO.getOrderCountByStatus("DELIVERED");

			// Get recent orders
			List<Order> recentOrders = orderDAO.getOrdersByStatus("PENDING");

			request.setAttribute("totalUsers", totalUsers);
			request.setAttribute("totalRestaurants", totalRestaurants);
			request.setAttribute("activeRestaurants", activeRestaurants);
			request.setAttribute("pendingOrders", pendingOrders);
			request.setAttribute("totalOrders", totalOrders);
			request.setAttribute("recentOrders", recentOrders);
			request.setAttribute("pageTitle", "Admin Dashboard");

			request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error loading admin dashboard: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to load dashboard data.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle users management
	 */
	private void handleUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<User> users = userDAO.getAllUsers();
			request.setAttribute("users", users);
			request.setAttribute("pageTitle", "Manage Users");

			request.getRequestDispatcher("/admin/users.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error loading users: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to load users.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle restaurants management
	 */
	private void handleRestaurants(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();
			request.setAttribute("restaurants", restaurants);
			request.setAttribute("pageTitle", "Manage Restaurants");

			request.getRequestDispatcher("/admin/restaurants.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error loading restaurants: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to load restaurants.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle orders management
	 */
	private void handleOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String status = request.getParameter("status");
			List<Order> orders;

			if (status != null && !status.trim().isEmpty()) {
				orders = orderDAO.getOrdersByStatus(status);
				request.setAttribute("selectedStatus", status);
			} else {
				orders = orderDAO.getOrdersByStatus("PENDING");
				request.setAttribute("selectedStatus", "PENDING");
			}

			request.setAttribute("orders", orders);
			request.setAttribute("pageTitle", "Manage Orders");

			request.getRequestDispatcher("/admin/orders.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error loading orders: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to load orders.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle statistics page
	 */
	private void handleStatistics(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Get various statistics
			int totalUsers = userDAO.getAllUsers().size();
			int totalRestaurants = restaurantDAO.getAllRestaurants().size();
			int activeRestaurants = restaurantDAO.getActiveRestaurants().size();

			int pendingOrders = orderDAO.getOrderCountByStatus("PENDING");
			int confirmedOrders = orderDAO.getOrderCountByStatus("CONFIRMED");
			int preparingOrders = orderDAO.getOrderCountByStatus("PREPARING");
			int deliveredOrders = orderDAO.getOrderCountByStatus("DELIVERED");

			request.setAttribute("totalUsers", totalUsers);
			request.setAttribute("totalRestaurants", totalRestaurants);
			request.setAttribute("activeRestaurants", activeRestaurants);
			request.setAttribute("pendingOrders", pendingOrders);
			request.setAttribute("confirmedOrders", confirmedOrders);
			request.setAttribute("preparingOrders", preparingOrders);
			request.setAttribute("deliveredOrders", deliveredOrders);
			request.setAttribute("pageTitle", "System Statistics");

			request.getRequestDispatcher("/admin/statistics.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error loading statistics: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to load statistics.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle activate restaurant
	 */
	private void handleActivateRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String restaurantIdStr = request.getParameter("restaurantId");
			int restaurantId = Integer.parseInt(restaurantIdStr);

			boolean success = restaurantDAO.setRestaurantStatus(restaurantId, true);

			if (success) {
				request.setAttribute("success", "Restaurant activated successfully.");
			} else {
				request.setAttribute("error", "Failed to activate restaurant.");
			}

			response.sendRedirect(request.getContextPath() + "/admin/restaurants");

		} catch (Exception e) {
			System.err.println("Error activating restaurant: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to activate restaurant.");
			response.sendRedirect(request.getContextPath() + "/admin/restaurants");
		}
	}

	/**
	 * Handle deactivate restaurant
	 */
	private void handleDeactivateRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String restaurantIdStr = request.getParameter("restaurantId");
			int restaurantId = Integer.parseInt(restaurantIdStr);

			boolean success = restaurantDAO.setRestaurantStatus(restaurantId, false);

			if (success) {
				request.setAttribute("success", "Restaurant deactivated successfully.");
			} else {
				request.setAttribute("error", "Failed to deactivate restaurant.");
			}

			response.sendRedirect(request.getContextPath() + "/admin/restaurants");

		} catch (Exception e) {
			System.err.println("Error deactivating restaurant: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to deactivate restaurant.");
			response.sendRedirect(request.getContextPath() + "/admin/restaurants");
		}
	}

	/**
	 * Handle delete user
	 */
	private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String userIdStr = request.getParameter("userId");
			int userId = Integer.parseInt(userIdStr);

			// Don't allow admin to delete themselves
			User currentUser = (User) request.getSession().getAttribute("user");
			if (currentUser.getUserId() == userId) {
				request.setAttribute("error", "You cannot delete your own account.");
				response.sendRedirect(request.getContextPath() + "/admin/users");
				return;
			}

			boolean success = userDAO.deleteUser(userId);

			if (success) {
				request.setAttribute("success", "User deleted successfully.");
			} else {
				request.setAttribute("error", "Failed to delete user.");
			}

			response.sendRedirect(request.getContextPath() + "/admin/users");

		} catch (Exception e) {
			System.err.println("Error deleting user: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to delete user.");
			response.sendRedirect(request.getContextPath() + "/admin/users");
		}
	}
}