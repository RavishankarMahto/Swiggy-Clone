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
 * RegisterServlet - Handles user registration functionality Creates new user
 * accounts with validation
 */
//@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

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

		// Forward to registration page
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Get form parameters
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String role = request.getParameter("role");

			// Validate input
			String validationError = validateInput(username, email, password, confirmPassword, phone, address, role);

			if (validationError != null) {
				request.setAttribute("error", validationError);
				setFormData(request, username, email, phone, address, role);
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			}

			// Check if username already exists
			if (userDAO.usernameExists(username)) {
				request.setAttribute("error", "Username already exists. Please choose a different username.");
				setFormData(request, username, email, phone, address, role);
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			}

			// Check if email already exists
			if (userDAO.emailExists(email)) {
				request.setAttribute("error", "Email already exists. Please use a different email address.");
				setFormData(request, username, email, phone, address, role);
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			}

			// Create new user
			User newUser = new User(username, password, email);
			newUser.setPhone(phone);
			newUser.setAddress(address);
			newUser.setRole(role != null ? role : "CUSTOMER");

			// Save user to database
			boolean success = userDAO.addUser(newUser);

			if (success) {
				// Registration successful
				request.setAttribute("success", "Registration successful! Please login to continue.");
				response.sendRedirect(request.getContextPath() + "/login?success=true");
			} else {
				// Registration failed
				request.setAttribute("error", "Registration failed. Please try again.");
				setFormData(request, username, email, phone, address, role);
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}

		} catch (Exception e) {
			System.err.println("Error in RegisterServlet: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Registration failed. Please try again.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

	/**
	 * Validate form input
	 */
	private String validateInput(String username, String email, String password, String confirmPassword, String phone,
			String address, String role) {

		if (username == null || username.trim().isEmpty()) {
			return "Username is required.";
		}

		if (username.length() < 3) {
			return "Username must be at least 3 characters long.";
		}

		if (email == null || email.trim().isEmpty()) {
			return "Email is required.";
		}

		if (!isValidEmail(email)) {
			return "Please enter a valid email address.";
		}

		if (password == null || password.trim().isEmpty()) {
			return "Password is required.";
		}

		if (password.length() < 6) {
			return "Password must be at least 6 characters long.";
		}

		if (!password.equals(confirmPassword)) {
			return "Passwords do not match.";
		}

		if (phone != null && !phone.trim().isEmpty() && !isValidPhone(phone)) {
			return "Please enter a valid phone number.";
		}

		if (role != null && !isValidRole(role)) {
			return "Invalid role selected.";
		}

		return null; // No validation errors
	}

	/**
	 * Check if email is valid
	 */
	private boolean isValidEmail(String email) {
		return email.contains("@") && email.contains(".") && email.length() > 5;
	}

	/**
	 * Check if phone number is valid
	 */
	private boolean isValidPhone(String phone) {
		return phone.matches("\\d{10,15}"); // 10-15 digits
	}

	/**
	 * Check if role is valid
	 */
	private boolean isValidRole(String role) {
		return "CUSTOMER".equals(role) || "RESTAURANT_OWNER".equals(role);
	}

	/**
	 * Set form data for repopulating form in case of error
	 */
	private void setFormData(HttpServletRequest request, String username, String email, String phone, String address,
			String role) {
		request.setAttribute("username", username);
		request.setAttribute("email", email);
		request.setAttribute("phone", phone);
		request.setAttribute("address", address);
		request.setAttribute("selectedRole", role);
	}
}