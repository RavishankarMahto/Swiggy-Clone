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
 * ProfileServlet - Handles user profile management
 * View and update user profile information
 */
//@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            User user = (User) session.getAttribute("user");
            
            // Refresh user data from database
            User updatedUser = userDAO.getUserById(user.getUserId());
            if (updatedUser != null) {
                session.setAttribute("user", updatedUser);
                request.setAttribute("user", updatedUser);
            } else {
                request.setAttribute("error", "User profile not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("pageTitle", "My Profile");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in ProfileServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while loading your profile.");
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
            if ("update".equals(action)) {
                handleUpdateProfile(request, response);
            } else if ("changePassword".equals(action)) {
                handleChangePassword(request, response);
            } else {
                doGet(request, response);
            }
        } catch (Exception e) {
            System.err.println("Error in ProfileServlet POST: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating your profile.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle profile update
     */
    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");
        
        try {
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            
            // Validate input
            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("error", "Email is required.");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                return;
            }
            
            if (!isValidEmail(email)) {
                request.setAttribute("error", "Please enter a valid email address.");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                return;
            }
            
            if (phone != null && !phone.trim().isEmpty() && !isValidPhone(phone)) {
                request.setAttribute("error", "Please enter a valid phone number.");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                return;
            }
            
            // Check if email is already taken by another user
            if (!email.equals(currentUser.getEmail())) {
                if (userDAO.emailExists(email)) {
                    request.setAttribute("error", "Email already exists. Please use a different email.");
                    request.getRequestDispatcher("/profile.jsp").forward(request, response);
                    return;
                }
            }
            
            // Update user information
            currentUser.setEmail(email);
            currentUser.setPhone(phone);
            currentUser.setAddress(address);
            
            boolean success = userDAO.updateUser(currentUser);
            
            if (success) {
                // Update session
                session.setAttribute("user", currentUser);
                request.setAttribute("success", "Profile updated successfully!");
                request.setAttribute("user", currentUser);
            } else {
                request.setAttribute("error", "Failed to update profile. Please try again.");
                request.setAttribute("user", currentUser);
            }
            
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error updating profile: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Failed to update profile. Please try again.");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        }
    }
    
    /**
     * Handle password change
     */
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");
        
        try {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            
            // Validate input
            if (currentPassword == null || currentPassword.trim().isEmpty()) {
                request.setAttribute("passwordError", "Current password is required.");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                return;
            }
            
            if (newPassword == null || newPassword.trim().isEmpty()) {
                request.setAttribute("passwordError", "New password is required.");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                return;
            }
            
            if (newPassword.length() < 6) {
                request.setAttribute("passwordError", "New password must be at least 6 characters long.");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("passwordError", "New passwords do not match.");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                return;
            }
            
            // Verify current password
            if (!currentPassword.equals(currentUser.getPassword())) {
                request.setAttribute("passwordError", "Current password is incorrect.");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
                return;
            }
            
            // Update password
            currentUser.setPassword(newPassword);
            boolean success = userDAO.updateUser(currentUser);
            
            if (success) {
                // Update session
                session.setAttribute("user", currentUser);
                request.setAttribute("passwordSuccess", "Password changed successfully!");
            } else {
                request.setAttribute("passwordError", "Failed to change password. Please try again.");
            }
            
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error changing password: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("passwordError", "Failed to change password. Please try again.");
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        }
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
}