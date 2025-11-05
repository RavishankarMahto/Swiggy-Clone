package com.fooddelivery.servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the current session
        HttpSession session = request.getSession(false); // false -> don't create new session
        if (session == null || session.getAttribute("user") == null) {
            // No user in session, redirect to login page
            response.sendRedirect("login.jsp");
            return;
        }

        // User is logged in, forward to welcome.jsp
        request.getRequestDispatcher("welcome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forwards POST requests to GET
        doGet(request, response);
    }
}

