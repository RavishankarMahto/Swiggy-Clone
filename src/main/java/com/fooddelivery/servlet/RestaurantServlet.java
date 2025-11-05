package com.fooddelivery.servlet;

import java.io.IOException;
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


//@WebServlet("/restaurant")
public class RestaurantServlet extends HttpServlet {

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
			String restaurantIdStr = request.getParameter("id");

			if (restaurantIdStr == null || restaurantIdStr.trim().isEmpty()) {
				request.setAttribute("error", "Restaurant ID is required.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			int restaurantId;
			try {
				restaurantId = Integer.parseInt(restaurantIdStr);
			} catch (NumberFormatException e) {
				request.setAttribute("error", "Invalid restaurant ID.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);

			if (restaurant == null) {
				request.setAttribute("error", "Restaurant not found.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			List<Menu> menuItems = menuDAO.getAvailableMenuItemsByRestaurant(restaurantId);

			List<String> categories = menuDAO.getCategoriesByRestaurant(restaurantId);

			request.setAttribute("restaurant", restaurant);
			request.setAttribute("menuItems", menuItems);
			request.setAttribute("categories", categories);
			request.setAttribute("pageTitle", restaurant.getName());

			request.getRequestDispatcher("/restaurant.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error in RestaurantServlet: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while loading restaurant details.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("filter".equals(action)) {
			handleMenuFilter(request, response);
		} else if ("search".equals(action)) {
			handleMenuSearch(request, response);
		} else {
			doGet(request, response);
		}
	}

	private void handleMenuFilter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String restaurantIdStr = request.getParameter("restaurantId");
			String category = request.getParameter("category");

			if (restaurantIdStr == null || restaurantIdStr.trim().isEmpty()) {
				request.setAttribute("error", "Restaurant ID is required.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			int restaurantId = Integer.parseInt(restaurantIdStr);

			Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);

			if (restaurant == null) {
				request.setAttribute("error", "Restaurant not found.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			List<Menu> menuItems;
			if (category != null && !category.trim().isEmpty()) {
				menuItems = menuDAO.getMenuItemsByCategory(restaurantId, category);
			} else {
				menuItems = menuDAO.getAvailableMenuItemsByRestaurant(restaurantId);
			}

			List<String> categories = menuDAO.getCategoriesByRestaurant(restaurantId);

			request.setAttribute("restaurant", restaurant);
			request.setAttribute("menuItems", menuItems);
			request.setAttribute("categories", categories);
			request.setAttribute("selectedCategory", category);
			request.setAttribute("pageTitle", restaurant.getName());

			request.getRequestDispatcher("/restaurant.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error filtering menu: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Error filtering menu items.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void handleMenuSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String restaurantIdStr = request.getParameter("restaurantId");
			String searchTerm = request.getParameter("searchTerm");

			if (restaurantIdStr == null || restaurantIdStr.trim().isEmpty()) {
				request.setAttribute("error", "Restaurant ID is required.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			int restaurantId = Integer.parseInt(restaurantIdStr);

			Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);

			if (restaurant == null) {
				request.setAttribute("error", "Restaurant not found.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			List<Menu> menuItems;
			if (searchTerm != null && !searchTerm.trim().isEmpty()) {
				menuItems = menuDAO.searchMenuItems(restaurantId, searchTerm.trim());
			} else {
				menuItems = menuDAO.getAvailableMenuItemsByRestaurant(restaurantId);
			}

			List<String> categories = menuDAO.getCategoriesByRestaurant(restaurantId);

			request.setAttribute("restaurant", restaurant);
			request.setAttribute("menuItems", menuItems);
			request.setAttribute("categories", categories);
			request.setAttribute("searchTerm", searchTerm);
			request.setAttribute("pageTitle", restaurant.getName());

			request.getRequestDispatcher("/restaurant.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error searching menu: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Error searching menu items.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}