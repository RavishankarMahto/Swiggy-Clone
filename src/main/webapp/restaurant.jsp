<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${pageTitle}-Food Delivery</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<style>
.restaurant-header {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: white;
	padding: 3rem 0;
}

.menu-item-card {
	transition: transform 0.3s ease;
	border: none;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.menu-item-card:hover {
	transform: translateY(-3px);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.category-section {
	margin-bottom: 3rem;
}

.category-header {
	border-bottom: 3px solid #667eea;
	padding-bottom: 0.5rem;
	margin-bottom: 1.5rem;
}

.price-tag {
	background: linear-gradient(45deg, #ff6b6b, #ee5a24);
	color: white;
	padding: 8px 15px;
	border-radius: 20px;
	font-weight: bold;
	font-size: 1.1em;
}

.add-to-cart-btn {
	background: linear-gradient(45deg, #28a745, #20c997);
	border: none;
	border-radius: 25px;
	padding: 8px 20px;
	color: white;
	font-weight: 500;
	transition: all 0.3s ease;
}

.add-to-cart-btn:hover {
	transform: scale(1.05);
	box-shadow: 0 4px 8px rgba(40, 167, 69, 0.3);
}

.filter-section {
	background: #f8f9fa;
	border-radius: 10px;
	padding: 1.5rem;
	margin-bottom: 2rem;
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
					<c:choose>
						<c:when test="${sessionScope.user != null}">
							<li class="nav-item"><a class="nav-link" href="cart"> <i
									class="fas fa-shopping-cart me-1"></i>Cart
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
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link" href="login">Login</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="register">Register</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Restaurant Header -->
	<c:if test="${restaurant != null}">
		<section class="restaurant-header">
			<div class="container">
				<div class="row align-items-center">
					<div class="col-lg-8">
						<h1 class="display-4 fw-bold mb-3">${restaurant.name}</h1>
						<p class="lead mb-3">${restaurant.description}</p>

						<div class="row">
							<div class="col-md-6">
								<div class="mb-2">
									<i class="fas fa-star text-warning me-2"></i> <strong>${restaurant.rating}/5</strong>
									Rating
								</div>
								<div class="mb-2">
									<i class="fas fa-clock me-2"></i> <strong>${restaurant.deliveryTime}
										min</strong> Delivery Time
								</div>
							</div>
							<div class="col-md-6">
								<div class="mb-2">
									<i class="fas fa-truck me-2"></i> <strong>$${restaurant.deliveryFee}</strong>
									Delivery Fee
								</div>
								<div class="mb-2">
									<i class="fas fa-utensils me-2"></i> <strong>${restaurant.cuisineType}</strong>
									Cuisine
								</div>
							</div>
						</div>

						<div class="mt-3">
							<i class="fas fa-map-marker-alt me-2"></i> <small>${restaurant.address}</small>
						</div>
					</div>
					<div class="col-lg-4 text-center">
						<i class="fas fa-store fa-5x opacity-50"></i>
					</div>
				</div>
			</div>
		</section>
	</c:if>

	<!-- Filter Section -->
	<section class="py-4">
		<div class="container">
			<div class="filter-section">
				<div class="row align-items-center">
					<div class="col-md-6">
						<h5 class="mb-3">Filter Menu:</h5>
						<form action="restaurant" method="post" class="d-flex">
							<input type="hidden" name="action" value="filter"> <input
								type="hidden" name="restaurantId"
								value="${restaurant.restaurantId}"> <select
								name="category" class="form-select me-2">
								<option value="">All Categories</option>
								<c:forEach var="category" items="${categories}">
									<option value="${category}"
										${selectedCategory == category ? 'selected' : ''}>
										${category}</option>
								</c:forEach>
							</select>
							<button type="submit" class="btn btn-primary">Filter</button>
						</form>
					</div>
					<div class="col-md-6">
						<h5 class="mb-3">Search Menu:</h5>
						<form action="restaurant" method="post" class="d-flex">
							<input type="hidden" name="action" value="search"> <input
								type="hidden" name="restaurantId"
								value="${restaurant.restaurantId}"> <input type="text"
								name="searchTerm" class="form-control me-2"
								placeholder="Search menu items..." value="${searchTerm}">
							<button type="submit" class="btn btn-outline-primary">Search</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Menu Section -->
	<section class="py-5">
		<div class="container">
			<c:choose>
				<c:when test="${menuItems != null && !menuItems.isEmpty()}">
					<c:set var="currentCategory" value="" />
					<c:forEach var="item" items="${menuItems}">
						<c:if test="${currentCategory != item.category}">
							<c:if test="${currentCategory != ''}">
		</div>
		<!-- Close previous category section -->
		</c:if>
		<c:set var="currentCategory" value="${item.category}" />
		<div class="category-section">
			<h3 class="category-header">${item.category}</h3>
			<div class="row">
				</c:if>

				<div class="col-lg-6 mb-4">
					<div class="card menu-item-card h-100">
						<div class="card-body">
							<div class="row align-items-center">
								<div class="col-8">
									<h5 class="card-title mb-2">${item.name}</h5>
									<p class="card-text text-muted mb-3">${item.description}</p>
									<div class="d-flex align-items-center justify-content-between">
										<div class="price-tag">$${item.price}</div>
										<c:if test="${sessionScope.user != null}">
											<form action="cart" method="post" class="d-inline">
												<input type="hidden" name="action" value="add"> <input
													type="hidden" name="itemId" value="${item.itemId}">
												<input type="hidden" name="quantity" value="1"> <input
													type="hidden" name="redirectUrl"
													value="restaurant?id=${restaurant.restaurantId}">
												<button type="submit" class="add-to-cart-btn">
													<i class="fas fa-plus me-1"></i>Add to Cart
												</button>
											</form>
										</c:if>
										<c:if test="${sessionScope.user == null}">
											<a href="login" class="btn btn-outline-primary"> <i
												class="fas fa-sign-in-alt me-1"></i>Login to Order
											</a>
										</c:if>
									</div>
								</div>
								<div class="col-4 text-center">
									<c:choose>
										<c:when
											test="${item.imageUrl != null && !item.imageUrl.isEmpty()}">
											<img src="${item.imageUrl}" alt="${item.name}"
												class="img-fluid rounded" style="max-height: 100px;">
										</c:when>
										<c:otherwise>
											<i class="fas fa-utensils fa-3x text-muted"></i>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
				</div>
				</c:forEach>
			</div>
			<!-- Close last category section -->
			</c:when>
			<c:otherwise>
				<div class="text-center py-5">
					<i class="fas fa-utensils fa-4x text-muted mb-3"></i>
					<h4 class="text-muted">No menu items found</h4>
					<p class="text-muted">Try adjusting your search or filter
						criteria.</p>
					<a href="restaurant?id=${restaurant.restaurantId}"
						class="btn btn-primary">View All Items</a>
				</div>
			</c:otherwise>
			</c:choose>
		</div>
	</section>

	<!-- Back to Restaurants -->
	<section class="py-4 bg-light">
		<div class="container text-center">
			<a href="home" class="btn btn-outline-primary"> <i
				class="fas fa-arrow-left me-2"></i>Back to Restaurants
			</a>
		</div>
	</section>

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