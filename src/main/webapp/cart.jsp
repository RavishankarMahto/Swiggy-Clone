<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Shopping Cart - Food Delivery</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<style>
.cart-header {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: white;
	padding: 2rem 0;
}

.cart-item {
	border: none;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	margin-bottom: 1rem;
}

.quantity-controls {
	display: flex;
	align-items: center;
	gap: 10px;
}

.quantity-btn {
	width: 35px;
	height: 35px;
	border-radius: 50%;
	border: 1px solid #dee2e6;
	background: white;
	display: flex;
	align-items: center;
	justify-content: center;
	cursor: pointer;
	transition: all 0.3s ease;
}

.quantity-btn:hover {
	background: #667eea;
	color: white;
	border-color: #667eea;
}

.quantity-input {
	width: 60px;
	text-align: center;
	border: 1px solid #dee2e6;
	border-radius: 5px;
	padding: 5px;
}

.remove-btn {
	background: #dc3545;
	color: white;
	border: none;
	border-radius: 5px;
	padding: 5px 10px;
	transition: all 0.3s ease;
}

.remove-btn:hover {
	background: #c82333;
	transform: scale(1.05);
}

.checkout-section {
	background: #f8f9fa;
	border-radius: 10px;
	padding: 2rem;
	position: sticky;
	top: 20px;
}

.checkout-btn {
	background: linear-gradient(45deg, #28a745, #20c997);
	border: none;
	border-radius: 10px;
	padding: 15px;
	font-weight: 600;
	font-size: 1.1em;
	text-transform: uppercase;
	letter-spacing: 1px;
}

.checkout-btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 5px 15px rgba(40, 167, 69, 0.3);
}

.empty-cart {
	text-align: center;
	padding: 4rem 2rem;
}

.empty-cart i {
	font-size: 4rem;
	color: #ccc;
	margin-bottom: 1rem;
}
</style>
</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="home"> <i
				class="fas fa-utensils me-2"></i>Food Delivery
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link" href="home">Home</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="search">Search</a>
					</li>
				</ul>
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link active" href="cart">
							<i class="fas fa-shopping-cart me-1"></i>Cart <c:if
								test="${itemCount > 0}">
								<span class="badge bg-primary">${itemCount}</span>
							</c:if>
					</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-bs-toggle="dropdown"> <i
							class="fas fa-user me-1"></i>${sessionScope.user.username}
					</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="profile">My Profile</a></li>
							<li><a class="dropdown-item" href="order">My Orders</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="logout">Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Cart Header -->
	<section class="cart-header">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-8">
					<h1 class="display-5 fw-bold mb-3">
						<i class="fas fa-shopping-cart me-3"></i>Shopping Cart
					</h1>
					<p class="lead mb-0">Review your items before checkout</p>
				</div>
				<div class="col-lg-4 text-end">
					<c:if test="${itemCount > 0}">
						<h4 class="mb-0">${itemCount}item(s) in cart</h4>
					</c:if>
				</div>
			</div>
		</div>
	</section>

	<!-- Cart Content -->
	<section class="py-5">
		<div class="container">
			<c:choose>
				<c:when test="${cart != null && !cart.isEmpty()}">
					<div class="row">
						<!-- Cart Items -->
						<div class="col-lg-8">
							<c:forEach var="item" items="${cart}">
								<div class="card cart-item">
									<div class="card-body">
										<div class="row align-items-center">
											<div class="col-md-2">
												<i class="fas fa-utensils fa-3x text-muted"></i>
											</div>
											<div class="col-md-4">
												<h5 class="card-title mb-1">${item.itemName}</h5>
												<p class="card-text text-muted small">${item.itemDescription}</p>
												<div class="text-primary fw-bold">$${item.price}</div>
											</div>
											<div class="col-md-3">
												<div class="quantity-controls">
													<form action="cart" method="post" class="d-inline">
														<input type="hidden" name="action" value="update">
														<input type="hidden" name="itemId" value="${item.itemId}">
														<input type="hidden" name="quantity"
															value="${item.quantity - 1}">
														<button type="submit" class="quantity-btn"
															${item.quantity <= 1 ? 'disabled' : ''}>
															<i class="fas fa-minus"></i>
														</button>
													</form>

													<span class="fw-bold">${item.quantity}</span>

													<form action="cart" method="post" class="d-inline">
														<input type="hidden" name="action" value="update">
														<input type="hidden" name="itemId" value="${item.itemId}">
														<input type="hidden" name="quantity"
															value="${item.quantity + 1}">
														<button type="submit" class="quantity-btn">
															<i class="fas fa-plus"></i>
														</button>
													</form>
												</div>
											</div>
											<div class="col-md-2">
												<div class="text-end">
													<div class="fw-bold text-success">$${item.totalPrice}</div>
												</div>
											</div>
											<div class="col-md-1">
												<form action="cart" method="post" class="d-inline">
													<input type="hidden" name="action" value="remove">
													<input type="hidden" name="itemId" value="${item.itemId}">
													<button type="submit" class="remove-btn"
														onclick="return confirm('Remove this item from cart?')">
														<i class="fas fa-trash"></i>
													</button>
												</form>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>

							<!-- Clear Cart Button -->
							<div class="text-center mt-4">
								<form action="cart" method="post" class="d-inline">
									<input type="hidden" name="action" value="clear">
									<button type="submit" class="btn btn-outline-danger"
										onclick="return confirm('Clear entire cart?')">
										<i class="fas fa-trash-alt me-2"></i>Clear Cart
									</button>
								</form>
							</div>
						</div>

						<!-- Checkout Section -->
						<div class="col-lg-4">
							<div class="checkout-section">
								<h4 class="mb-4">Order Summary</h4>

								<div class="d-flex justify-content-between mb-2">
									<span>Subtotal:</span> <span>$${subtotal}</span>
								</div>

								<div class="d-flex justify-content-between mb-2">
									<span>Delivery Fee:</span> <span>$${deliveryFee}</span>
								</div>

								<hr>

								<div class="d-flex justify-content-between mb-4">
									<strong>Total:</strong> <strong class="text-success">$${total}</strong>
								</div>

								<button type="button"
									class="btn btn-success checkout-btn w-100 mb-3"
									data-bs-toggle="modal" data-bs-target="#checkoutModal">
									<i class="fas fa-credit-card me-2"></i>Proceed to Checkout
								</button>

								<a href="home" class="btn btn-outline-primary w-100"> <i
									class="fas fa-arrow-left me-2"></i>Continue Shopping
								</a>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<!-- Empty Cart -->
					<div class="empty-cart">
						<i class="fas fa-shopping-cart"></i>
						<h3 class="text-muted">Your cart is empty</h3>
						<p class="text-muted mb-4">Add some delicious items to get
							started!</p>
						<a href="home" class="btn btn-primary btn-lg"> <i
							class="fas fa-utensils me-2"></i>Start Shopping
						</a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</section>

	<!-- Checkout Modal -->
	<div class="modal fade" id="checkoutModal" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Complete Your Order</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<form action="order" method="post" id="checkoutForm">
						<input type="hidden" name="action" value="create"> <input
							type="hidden" name="restaurantId" value="1">
						<!-- You'll need to set this dynamically -->

						<div class="mb-3">
							<label for="deliveryAddress" class="form-label">Delivery
								Address *</label>
							<textarea class="form-control" id="deliveryAddress"
								name="deliveryAddress" rows="3" required
								placeholder="Enter your delivery address">${sessionScope.user.address}</textarea>
						</div>

						<div class="mb-3">
							<label for="notes" class="form-label">Special
								Instructions</label>
							<textarea class="form-control" id="notes" name="notes" rows="2"
								placeholder="Any special instructions for your order?"></textarea>
						</div>

						<div class="mb-3">
							<label class="form-label">Payment Method</label>
							<div class="form-check">
								<input class="form-check-input" type="radio"
									name="paymentMethod" id="cash" value="cash" checked> <label
									class="form-check-label" for="cash"> <i
									class="fas fa-money-bill-wave me-2"></i>Cash on Delivery
								</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio"
									name="paymentMethod" id="card" value="card"> <label
									class="form-check-label" for="card"> <i
									class="fas fa-credit-card me-2"></i>Credit Card
								</label>
							</div>
						</div>

						<div class="alert alert-info">
							<i class="fas fa-info-circle me-2"></i> <strong>Order
								Total: $${total}</strong><br> <small>Including delivery fee
								and taxes</small>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
					<button type="submit" form="checkoutForm" class="btn btn-success">
						<i class="fas fa-check me-2"></i>Place Order
					</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer class="bg-dark text-white py-4">
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<h5>Food Delivery</h5>
					<p>Delivering happiness, one meal at a time.</p>
				</div>
				<div class="col-md-6 text-end">
					<p>&copy; 2024 Food Delivery App. All rights reserved.</p>
				</div>
			</div>
		</div>
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

