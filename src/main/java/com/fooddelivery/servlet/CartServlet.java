package com.fooddelivery.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.dao.MenuDAO;
import com.fooddelivery.daoimpl.MenuDAOImpl;
import com.fooddelivery.model.Menu;
import com.fooddelivery.model.OrderItem;

import jakarta.servlet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * CartServlet - Handles shopping cart functionality
Add, remove, update items
 * in cart
 */
//@WebServlet("/cart")
public class CartServlet extends HttpServlet {

	private MenuDAO menuDAO;

	@Override
	public void init() throws ServletException {
		menuDAO = new MenuDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("user") == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			@SuppressWarnings("unchecked")
			List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");

			if (cart == null) {
				cart = new ArrayList<>();
				session.setAttribute("cart", cart);
			}

			// Calculate cart totals
			BigDecimal subtotal = calculateSubtotal(cart);
			BigDecimal deliveryFee = calculateDeliveryFee(cart);
			BigDecimal total = subtotal.add(deliveryFee);

			request.setAttribute("cart", cart);
			request.setAttribute("subtotal", subtotal);
			request.setAttribute("deliveryFee", deliveryFee);
			request.setAttribute("total", total);
			request.setAttribute("itemCount", cart.size());
			request.setAttribute("pageTitle", "Shopping Cart");

			request.getRequestDispatcher("/cart.jsp").forward(request, response);

		} catch (Exception e) {
			System.err.println("Error in CartServlet: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while loading your cart.");
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
			if ("add".equals(action)) {
				handleAddToCart(request, response);
			} else if ("remove".equals(action)) {
				handleRemoveFromCart(request, response);
			} else if ("update".equals(action)) {
				handleUpdateCart(request, response);
			} else if ("clear".equals(action)) {
				handleClearCart(request, response);
			} else {
				doGet(request, response);
			}
		} catch (Exception e) {
			System.err.println("Error in CartServlet POST: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while updating your cart.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle add item to cart
	 */
	private void handleAddToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String itemIdStr = request.getParameter("itemId");
			String quantityStr = request.getParameter("quantity");

			if (itemIdStr == null || quantityStr == null) {
				request.setAttribute("error", "Item ID and quantity are required.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			int itemId = Integer.parseInt(itemIdStr);
			int quantity = Integer.parseInt(quantityStr);

			if (quantity <= 0) {
				request.setAttribute("error", "Quantity must be greater than 0.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			// Get menu item
			Menu menuItem = menuDAO.getMenuItemById(itemId);
			if (menuItem == null || !menuItem.isAvailable()) {
				request.setAttribute("error", "Item not available.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			HttpSession session = request.getSession(true);
			@SuppressWarnings("unchecked")
			List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");

			if (cart == null) {
				cart = new ArrayList<>();
				session.setAttribute("cart", cart);
			}

			// Check if item already exists in cart
			boolean itemExists = false;
			for (OrderItem cartItem : cart) {
				if (cartItem.getItemId() == itemId) {
					// Update quantity
					cartItem.setQuantity(cartItem.getQuantity() + quantity);
					itemExists = true;
					break;
				}
			}

			if (!itemExists) {
				// Add new item to cart
				OrderItem orderItem = new OrderItem();
				orderItem.setItemId(itemId);
				orderItem.setQuantity(quantity);
				orderItem.setPrice(menuItem.getPrice());
				orderItem.setItemName(menuItem.getName());
				orderItem.setItemDescription(menuItem.getDescription());

				cart.add(orderItem);
			}

			session.setAttribute("cart", cart);

			// Redirect back to restaurant page or cart
			String redirectUrl = request.getParameter("redirectUrl");
			if (redirectUrl != null && !redirectUrl.isEmpty()) {
				response.sendRedirect(redirectUrl + "?added=true");
			} else {
				response.sendRedirect(request.getContextPath() + "/cart?added=true");
			}

		} catch (Exception e) {
			System.err.println("Error adding to cart: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to add item to cart.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle remove item from cart
	 */
	private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String itemIdStr = request.getParameter("itemId");

			if (itemIdStr == null) {
				request.setAttribute("error", "Item ID is required.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			int itemId = Integer.parseInt(itemIdStr);

			HttpSession session = request.getSession(false);
			@SuppressWarnings("unchecked")
			List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");

			if (cart != null) {
				cart.removeIf(item -> item.getItemId() == itemId);
				session.setAttribute("cart", cart);
			}

			response.sendRedirect(request.getContextPath() + "/cart?removed=true");

		} catch (Exception e) {
			System.err.println("Error removing from cart: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to remove item from cart.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle update cart item quantity
	 */
	private void handleUpdateCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String itemIdStr = request.getParameter("itemId");
			String quantityStr = request.getParameter("quantity");

			if (itemIdStr == null || quantityStr == null) {
				request.setAttribute("error", "Item ID and quantity are required.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			int itemId = Integer.parseInt(itemIdStr);
			int quantity = Integer.parseInt(quantityStr);

			if (quantity <= 0) {
				// Remove item if quantity is 0 or negative
				handleRemoveFromCart(request, response);
				return;
			}

			HttpSession session = request.getSession(false);
			@SuppressWarnings("unchecked")
			List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");

			if (cart != null) {
				for (OrderItem cartItem : cart) {
					if (cartItem.getItemId() == itemId) {
						cartItem.setQuantity(quantity);
						break;
					}
				}
				session.setAttribute("cart", cart);
			}

			response.sendRedirect(request.getContextPath() + "/cart?updated=true");

		} catch (Exception e) {
			System.err.println("Error updating cart: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to update cart.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handle clear entire cart
	 */
	private void handleClearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute("cart");
			}

			response.sendRedirect(request.getContextPath() + "/cart?cleared=true");

		} catch (Exception e) {
			System.err.println("Error clearing cart: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Failed to clear cart.");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * Calculate cart subtotal
	 */
	private BigDecimal calculateSubtotal(List<OrderItem> cart) {
		BigDecimal subtotal = BigDecimal.ZERO;
		for (OrderItem item : cart) {
			subtotal = subtotal.add(item.getTotalPrice());
		}
		return subtotal;
	}

	/**
	 * Calculate delivery fee (simple implementation)
	 */
	private BigDecimal calculateDeliveryFee(List<OrderItem> cart) {
		if (cart.isEmpty()) {
			return BigDecimal.ZERO;
		}
		// Simple delivery fee calculation - you can make this more complex
		return new BigDecimal("2.99");
	}
}