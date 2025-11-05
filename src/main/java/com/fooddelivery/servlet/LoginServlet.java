package com.fooddelivery.servlet;

import java.io.IOException;

import com.fooddelivery.dao.UserDAO;
import com.fooddelivery.daoimpl.UserDAOImpl;
import com.fooddelivery.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * LoginServlet - Handles user login functionality Authenticates users and
 * manages sessions
 */
//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private UserDAO userDAO;

	@Override
	public void init() throws ServletException {
		userDAO = new UserDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Check if user is already logged in
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}

		// Forward to login page
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String usernameOrEmail = request.getParameter("username");
			String password = request.getParameter("password");

			// Validate input
			if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty() || password == null
					|| password.trim().isEmpty()) {

				request.setAttribute("error", "Username/Email and password are required.");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			// Authenticate user
			User user = userDAO.authenticateUser(usernameOrEmail.trim(), password);

			if (user != null) {
				// Create session and store user information
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				session.setAttribute("userId", user.getUserId());
				session.setAttribute("username", user.getUsername());
				session.setAttribute("userRole", user.getRole());

				// Set session timeout (30 minutes)
				session.setMaxInactiveInterval(30 * 60);

				// Redirect based on user role
				String redirectPath = getRedirectPath(user.getRole());
				response.sendRedirect(request.getContextPath() + redirectPath);

			} else {
				// Authentication failed
				request.setAttribute("error", "Invalid username/email or password.");
				request.setAttribute("username", usernameOrEmail); // Preserve username
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}

		} catch (Exception e) {
			System.err.println("Error in LoginServlet: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Login failed. Please try again.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	/**
	 * Get redirect path based on user role
	 */
	private String getRedirectPath(String userRole) {
		switch (userRole.toUpperCase()) {
		case "ADMIN":
			return "/admin/dashboard";
		case "RESTAURANT_OWNER":
			return "/restaurant/dashboard";
		case "CUSTOMER":
		default:
			return "/home";
		}
	}
}