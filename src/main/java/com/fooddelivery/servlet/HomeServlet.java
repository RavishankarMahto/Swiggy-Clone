package com.fooddelivery.servlet;

import java.io.IOException;
import java.util.List;

import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.daoimpl.RestaurantDAOImpl;
import com.fooddelivery.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * HomeServlet - Handles home page requests Displays restaurants and handles
 * search functionality
 */
//@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private RestaurantDAO restaurantDAO;

	@Override
	public void init() throws ServletException {
		restaurantDAO = new RestaurantDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String action = request.getParameter("action");

			if ("search".equals(action)) {
				handleSearch(request, response);
			} else if ("filter".equals(action)) {
				handleFilter(request, response);
			} else {
				handleDefault(request, response);
			}

		} catch (Exception e) {
			System.err.println("Error in HomeServlet: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while loading restaurants.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("search".equals(action)) {
			handleSearch(request, response);
		} else {
			doGet(request, response);
		}
	}

	/**
	 * Handle default home page - show all active restaurants
	 */
	private void handleDefault(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<Restaurant> restaurants = restaurantDAO.getActiveRestaurants();
			request.setAttribute("restaurants", restaurants);
			request.setAttribute("pageTitle", "Food Delivery - Home");

			request.getRequestDispatcher("/home.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error loading restaurants: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Unable to load restaurants. Please try again.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle restaurant search
	 */
	private void handleSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String searchTerm = request.getParameter("searchTerm");

			if (searchTerm != null && !searchTerm.trim().isEmpty()) {
				List<Restaurant> restaurants = restaurantDAO.searchRestaurants(searchTerm.trim());
				request.setAttribute("restaurants", restaurants);
				request.setAttribute("searchTerm", searchTerm);
				request.setAttribute("pageTitle", "Search Results for: " + searchTerm);
			} else {
				List<Restaurant> restaurants = restaurantDAO.getActiveRestaurants();
				request.setAttribute("restaurants", restaurants);
				request.setAttribute("pageTitle", "Food Delivery - Home");
			}

			request.getRequestDispatcher("/home.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error searching restaurants: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Search failed. Please try again.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle restaurant filtering by cuisine type
	 */
	private void handleFilter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String cuisineType = request.getParameter("cuisineType");

			if (cuisineType != null && !cuisineType.trim().isEmpty()) {
				List<Restaurant> restaurants = restaurantDAO.getRestaurantsByCuisine(cuisineType);
				request.setAttribute("restaurants", restaurants);
				request.setAttribute("selectedCuisine", cuisineType);
				request.setAttribute("pageTitle", "Cuisine: " + cuisineType);
			} else {
				List<Restaurant> restaurants = restaurantDAO.getActiveRestaurants();
				request.setAttribute("restaurants", restaurants);
				request.setAttribute("pageTitle", "Food Delivery - Home");
			}

			request.getRequestDispatcher("/home.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error filtering restaurants: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Filter failed. Please try again.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}