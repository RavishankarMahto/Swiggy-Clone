<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${restaurant.name} - Menu | SwiggyClone</title>
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
        
        .restaurant-header {
            background: linear-gradient(to right, #fc8019, #ff6b35);
            color: white;
            padding: 30px 0;
            margin-top: 70px;
        }
        
        .restaurant-name-large {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 8px;
        }
        
        .restaurant-info-row {
            display: flex;
            gap: 20px;
            align-items: center;
            flex-wrap: wrap;
            margin-top: 15px;
        }
        
        .info-item {
            display: flex;
            align-items: center;
            gap: 6px;
            font-size: 14px;
        }
        
        .rating-large {
            background: white;
            color: #48c479;
            padding: 6px 12px;
            border-radius: 6px;
            font-weight: 600;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }
        
        .menu-container {
            padding: 30px 0;
        }
        
        .category-section {
            margin-bottom: 40px;
        }
        
        .category-header {
            font-size: 20px;
            font-weight: 600;
            color: #282c3f;
            padding-bottom: 10px;
            border-bottom: 2px solid #fc8019;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .menu-item-card {
            background: white;
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            transition: all 0.3s ease;
            display: flex;
            gap: 15px;
        }
        
        .menu-item-card:hover {
            box-shadow: 0 4px 12px rgba(0,0,0,0.12);
            transform: translateY(-2px);
        }
        
        .menu-item-image {
            width: 150px;
            height: 150px;
            border-radius: 8px;
            object-fit: cover;
            flex-shrink: 0;
            background: linear-gradient(45deg, #667eea, #764ba2);
        }
        
        .menu-item-content {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        
        .menu-item-name {
            font-size: 18px;
            font-weight: 600;
            color: #282c3f;
            margin-bottom: 5px;
        }
        
        .menu-item-description {
            color: #686b78;
            font-size: 14px;
            margin-bottom: 10px;
            line-height: 1.5;
        }
        
        .menu-item-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: auto;
        }
        
        .menu-item-price {
            font-size: 18px;
            font-weight: 600;
            color: #282c3f;
        }
        
        .add-to-cart-btn {
            background: #fc8019;
            color: white;
            border: none;
            padding: 10px 24px;
            border-radius: 6px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }
        
        .add-to-cart-btn:hover {
            background: #ff6b35;
            transform: scale(1.05);
        }
        
        .veg-indicator {
            width: 16px;
            height: 16px;
            border: 1.5px solid #48c479;
            border-radius: 2px;
            position: relative;
        }
        
        .veg-indicator::after {
            content: '';
            position: absolute;
            top: 3px;
            left: 3px;
            width: 8px;
            height: 8px;
            background: #48c479;
            border-radius: 50%;
        }
        
        .nonveg-indicator {
            width: 16px;
            height: 16px;
            border: 1.5px solid #e43a47;
            border-radius: 2px;
            position: relative;
        }
        
        .nonveg-indicator::after {
            content: '';
            position: absolute;
            top: 3px;
            left: 3px;
            width: 8px;
            height: 8px;
            background: #e43a47;
            border-radius: 50%;
        }
        
        .category-nav {
            background: white;
            padding: 15px 0;
            position: sticky;
            top: 70px;
            z-index: 100;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            margin-bottom: 20px;
        }
        
        .category-link {
            color: #686b78;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 20px;
            margin: 0 5px;
            transition: all 0.3s;
            font-size: 14px;
        }
        
        .category-link:hover, .category-link.active {
            background: #fc8019;
            color: white;
        }
        
        .unavailable-badge {
            background: #e43a47;
            color: white;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .empty-menu {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 12px;
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
                        <a class="nav-link" href="home" style="color: #282c3f;">Home</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <c:if test="${sessionScope.user != null}">
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
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Restaurant Header -->
    <div class="restaurant-header">
        <div class="container">
            <div class="restaurant-name-large">${restaurant.name}</div>
            <div style="font-size: 14px; opacity: 0.9;">${restaurant.description}</div>
            <div class="restaurant-info-row">
                <div class="rating-large">
                    <i class="fas fa-star"></i> ${restaurant.rating > 0 ? restaurant.rating : '4.0'}
                </div>
                <div class="info-item">
                    <i class="fas fa-clock"></i> ${restaurant.deliveryTime} mins
                </div>
                <div class="info-item">
                    <i class="fas fa-rupee-sign"></i> ${restaurant.averagePrice} for two
                </div>
                <div class="info-item">
                    <i class="fas fa-map-marker-alt"></i> ${restaurant.address}
                </div>
            </div>
        </div>
    </div>

    <!-- Category Navigation -->
    <c:if test="${not empty categories}">
        <div class="category-nav">
            <div class="container">
                <div class="d-flex flex-wrap">
                    <a href="menu?restaurantId=${restaurant.restaurantId}" class="category-link ${empty selectedCategory ? 'active' : ''}">All</a>
                    <c:forEach var="cat" items="${categories}">
                        <a href="menu?restaurantId=${restaurant.restaurantId}&category=${cat}" 
                           class="category-link ${selectedCategory == cat ? 'active' : ''}">${cat}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>

    <!-- Menu Items -->
    <div class="menu-container">
        <div class="container">
            <c:choose>
                <c:when test="${not empty menuItems}">
                    <c:set var="currentCategory" value="" />
                    <c:forEach var="item" items="${menuItems}">
                        <c:if test="${item.category != currentCategory}">
                            <c:if test="${not empty currentCategory}">
                                </div>
                            </c:if>
                            <c:set var="currentCategory" value="${item.category}" />
                            <div class="category-section" data-category="${item.category}">
                                <div class="category-header">
                                    <i class="fas fa-utensils"></i> ${item.category}
                                </div>
                        </c:if>
                        
                        <div class="menu-item-card">
                            <img src="${item.imageUrl != null && item.imageUrl != '' ? item.imageUrl : 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=300&h=300&fit=crop'}" 
                                 alt="${item.name}" 
                                 class="menu-item-image"
                                 onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=300&h=300&fit=crop'">
                            <div class="menu-item-content">
                                <div>
                                    <div style="display: flex; align-items: center; gap: 8px; margin-bottom: 5px;">
                                        <div class="menu-item-name">${item.name}</div>
                                    </div>
                                    <div class="menu-item-description">
                                        ${item.description != null ? item.description : 'Delicious food item'}
                                    </div>
                                </div>
                                <div class="menu-item-footer">
                                    <div class="menu-item-price">₹${item.price}</div>
                                    <c:choose>
                                        <c:when test="${item.available}">
                                            <c:if test="${sessionScope.user != null}">
                                                <form action="cart" method="post" style="display: inline;">
                                                    <input type="hidden" name="action" value="add">
                                                    <input type="hidden" name="itemId" value="${item.itemId}">
                                                    <input type="hidden" name="restaurantId" value="${restaurant.restaurantId}">
                                                    <button type="submit" class="add-to-cart-btn">
                                                        <i class="fas fa-plus"></i> ADD
                                                    </button>
                                                </form>
                                            </c:if>
                                            <c:if test="${sessionScope.user == null}">
                                                <a href="login" class="add-to-cart-btn">
                                                    <i class="fas fa-plus"></i> ADD
                                                </a>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="unavailable-badge">Not Available</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    </div> <!-- Close last category section -->
                </c:when>
                <c:otherwise>
                    <div class="empty-menu">
                        <i class="fas fa-utensils" style="font-size: 80px; color: #d4d5d9; margin-bottom: 20px;"></i>
                        <h4 style="color: #686b78; margin-bottom: 10px;">No menu items found</h4>
                        <p style="color: #93959f;">This restaurant hasn't added any items yet</p>
                        <a href="home" class="btn btn-warning mt-3" style="background: #fc8019; border: none;">Browse Other Restaurants</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Smooth scroll to category
        document.querySelectorAll('.category-link').forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                const category = this.textContent.trim();
                const targetElement = document.querySelector(`[data-category="${category}"]`);
                if (targetElement) {
                    targetElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
                } else {
                    window.location.href = this.href;
                }
            });
        });
    </script>
</body>
</html>
