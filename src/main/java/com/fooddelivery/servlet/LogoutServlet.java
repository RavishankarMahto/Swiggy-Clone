package com.fooddelivery.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * LogoutServlet - Handles user logout functionality Invalidates session and
 * redirects to home page
 */
//@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		handleLogout(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		handleLogout(request, response);
	}

	/**
	 * Handle logout process
	 */
	private void handleLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Get current session
			HttpSession session = request.getSession(false);

			if (session != null) {
				// Get user information before logout for logging
				String username = (String) session.getAttribute("username");

				// Invalidate session
				session.invalidate();

				// Log logout activity
				System.out.println("User logged out: " + (username != null ? username : "Unknown"));
			}

			// Set logout message
			request.setAttribute("message", "You have been successfully logged out.");
			request.setAttribute("messageType", "success");

			// Redirect to home page with logout message
			response.sendRedirect(request.getContextPath() + "/home?logout=true");

		} catch (Exception e) {
			System.err.println("Error in LogoutServlet: " + e.getMessage());
			e.printStackTrace();

			// Even if there's an error, try to redirect to home
			response.sendRedirect(request.getContextPath() + "/home?error=logout_failed");
		}
	}
}