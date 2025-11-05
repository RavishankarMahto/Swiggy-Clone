<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SwiggyClone - Order Food Online</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
        }
        
        .navbar {
            background: #fff !important;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 0.8rem 0;
        }
        
        .navbar-brand {
            font-weight: 700;
            font-size: 1.5rem;
            color: #fc8019 !important;
        }
        
        .search-header {
            background: linear-gradient(to right, #fc8019, #ff6b35);
            padding: 40px 0;
            margin-bottom: 30px;
        }
        
        .search-input {
            border: none;
            border-radius: 8px;
            padding: 15px 20px;
            font-size: 16px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .restaurant-card {
            background: #fff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            transition: all 0.3s ease;
            cursor: pointer;
            margin-bottom: 25px;
            border: none;
        }
        
        .restaurant-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.15);
        }
        
        .restaurant-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            background: linear-gradient(45deg, #667eea, #764ba2);
        }
        
        .restaurant-info {
            padding: 15px;
        }
        
        .restaurant-name {
            font-size: 18px;
            font-weight: 600;
            color: #282c3f;
            margin-bottom: 8px;
        }
        
        .restaurant-cuisine {
            color: #686b78;
            font-size: 14px;
            margin-bottom: 10px;
        }
        
        .restaurant-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-top: 10px;
            border-top: 1px solid #e9ecef;
        }
        
        .rating-badge {
            background: #48c479;
            color: white;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 13px;
            font-weight: 600;
        }
        
        .delivery-info {
            color: #686b78;
            font-size: 13px;
        }
        
        .price-info {
            color: #686b78;
            font-size: 13px;
        }
        
        .filter-section {
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            margin-bottom: 30px;
        }
        
        .filter-btn {
            border: 1px solid #d4d5d9;
            background: white;
            color: #282c3f;
            padding: 8px 16px;
            border-radius: 20px;
            margin: 5px;
            font-size: 14px;
            transition: all 0.3s;
        }
        
        .filter-btn:hover, .filter-btn.active {
            background: #fc8019;
            color: white;
            border-color: #fc8019;
        }
        
        .page-title {
            font-size: 32px;
            font-weight: 700;
            color: #282c3f;
            margin-bottom: 20px;
        }
        
        .cuisine-tag {
            background: #f0f0f0;
            color: #686b78;
            padding: 4px 10px;
            border-radius: 15px;
            font-size: 12px;
            margin-right: 5px;
            display: inline-block;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
        }
        
        .empty-state img {
            width: 200px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-light fixed-top">
        <div class="container">
            <a class="navbar-brand" href="home">
                <i class="fas fa-utensils me-2"></i>SwiggyClone
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="home" style="color: #282c3f;">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="search" style="color: #686b78;">Search</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                            <li class="nav-item">
                                <a class="nav-link" href="cart" style="color: #282c3f;">
                                    <i class="fas fa-shopping-cart me-1"></i>Cart
                                </a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" style="color: #282c3f;">
                                    <i class="fas fa-user me-1"></i>${sessionScope.user.username}
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="profile">My Profile</a></li>
                                    <li><a class="dropdown-item" href="order">My Orders</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="logout">Logout</a></li>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="login" style="color: #282c3f;">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link btn btn-outline-warning ms-2" href="register" style="color: #fc8019;">Sign Up</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Search Header -->
    <div class="search-header" style="margin-top: 70px;">
        <div class="container">
            <div class="row">
                <div class="col-md-8 mx-auto">
                    <form action="search" method="get">
                        <div class="input-group">
                            <input type="text" class="form-control search-input" name="query" placeholder="Search for restaurants, food items..." autocomplete="off">
                            <button class="btn btn-warning" type="submit" style="background: white; color: #fc8019; border: none; padding: 0 30px;">
                                <i class="fas fa-search"></i> Search
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container" style="padding-bottom: 50px;">
        <h1 class="page-title">Restaurants</h1>
        
        <!-- Filters -->
        <div class="filter-section">
            <h6 class="mb-3" style="color: #282c3f; font-weight: 600;">Quick Filters</h6>
            <div class="d-flex flex-wrap">
                <a href="home" class="filter-btn active">All</a>
                <a href="home?filter=veg" class="filter-btn">Pure Veg</a>
                <a href="home?filter=nonveg" class="filter-btn">Non-Veg</a>
                <a href="home?filter=fast" class="filter-btn">Fast Delivery</a>
                <a href="home?filter=rating" class="filter-btn">High Rating</a>
            </div>
        </div>

        <!-- Restaurant Listings -->
        <div class="row">
            <c:choose>
                <c:when test="${not empty restaurants}">
                    <c:forEach var="restaurant" items="${restaurants}">
                        <div class="col-lg-4 col-md-6">
                            <div class="restaurant-card" onclick="window.location.href='menu?restaurantId=${restaurant.restaurantId}'">
                                <img src="${restaurant.imageUrl != null && restaurant.imageUrl != '' ? restaurant.imageUrl : 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400&h=300&fit=crop'}" 
                                     alt="${restaurant.name}" 
                                     class="restaurant-image"
                                     onerror="this.src='https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400&h=300&fit=crop'">
                                <div class="restaurant-info">
                                    <div class="restaurant-name">${restaurant.name}</div>
                                    <div class="restaurant-cuisine">
                                        <span class="cuisine-tag">${restaurant.cuisineType}</span>
                                    </div>
                                    <div class="restaurant-meta">
                                        <div>
                                            <span class="rating-badge">
                                                <i class="fas fa-star"></i> ${restaurant.rating > 0 ? restaurant.rating : '4.0'}
                                            </span>
                                        </div>
                                        <div class="delivery-info">
                                            <i class="fas fa-clock"></i> ${restaurant.deliveryTime} mins
                                        </div>
                                        <div class="price-info">
                                            ₹${restaurant.averagePrice} for two
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-12">
                        <div class="empty-state">
                            <i class="fas fa-utensils" style="font-size: 80px; color: #d4d5d9; margin-bottom: 20px;"></i>
                            <h4 style="color: #686b78; margin-bottom: 10px;">No restaurants found</h4>
                            <p style="color: #93959f;">Try adjusting your filters or search for something else</p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


