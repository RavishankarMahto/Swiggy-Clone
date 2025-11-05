<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> --%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders - Food Delivery</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .orders-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem 0;
        }
        .order-card {
            border: none;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-bottom: 1.5rem;
            transition: transform 0.3s ease;
        }
        .order-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }
        .status-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.8em;
            font-weight: 600;
            text-transform: uppercase;
        }
        .status-pending { background: #fff3cd; color: #856404; }
        .status-confirmed { background: #d1ecf1; color: #0c5460; }
        .status-preparing { background: #ffeaa7; color: #d63031; }
        .status-out-for-delivery { background: #74b9ff; color: white; }
        .status-delivered { background: #00b894; color: white; }
        .status-cancelled { background: #fab1a0; color: #d63031; }
        .empty-orders {
            text-align: center;
            padding: 4rem 2rem;
        }
        .empty-orders i {
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
            <a class="navbar-brand" href="home">
                <i class="fas fa-utensils me-2"></i>Food Delivery
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="search">Search</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="cart">
                            <i class="fas fa-shopping-cart me-1"></i>Cart
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-user me-1"></i>${sessionScope.user.username}
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="profile">My Profile</a></li>
                            <li><a class="dropdown-item active" href="order">My Orders</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Orders Header -->
    <section class="orders-header">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-8">
                    <h1 class="display-5 fw-bold mb-3">
                        <i class="fas fa-receipt me-3"></i>My Orders
                    </h1>
                    <p class="lead mb-0">Track your orders and view order history</p>
                </div>
                <div class="col-lg-4 text-end">
                    <a href="home" class="btn btn-light">
                        <i class="fas fa-plus me-2"></i>New Order
                    </a>
                </div>
            </div>
        </div>
    </section>

    <!-- Orders Content -->
    <section class="py-5">
        <div class="container">
            <c:choose>
                <c:when test="${orders != null && !orders.isEmpty()}">
                    <c:forEach var="order" items="${orders}">
                        <div class="card order-card">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col-md-2">
                                        <div class="text-center">
                                            <i class="fas fa-receipt fa-3x text-muted"></i>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <h5 class="card-title mb-2">Order #${order.orderId}</h5>
                                        <p class="card-text mb-1">
                                            <i class="fas fa-store me-2"></i>${order.restaurantName}
                                        </p>
                                        <p class="card-text mb-1">
                                            <i class="fas fa-calendar me-2"></i>
                                            <fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy HH:mm"/>
                                        </p>
                                        <p class="card-text mb-0">
                                            <i class="fas fa-map-marker-alt me-2"></i>${order.deliveryAddress}
                                        </p>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="mb-2">
                                            <span class="status-badge status-${order.status.toLowerCase().replace('_', '-')}">
                                                ${order.status.replace('_', ' ')}
                                            </span>
                                        </div>
                                        <div class="mb-2">
                                            <small class="text-muted">
                                                Payment: ${order.paymentStatus}
                                            </small>
                                        </div>
                                        <div>
                                            <strong class="text-success">$${order.totalAmount}</strong>
                                        </div>
                                    </div>
                                    <div class="col-md-3 text-end">
                                        <a href="order?action=view&orderId=${order.orderId}" class="btn btn-outline-primary btn-sm mb-2">
                                            <i class="fas fa-eye me-1"></i>View Details
                                        </a>
                                        <br>
                                        <a href="order?action=track&orderId=${order.orderId}" class="btn btn-outline-info btn-sm">
                                            <i class="fas fa-truck me-1"></i>Track Order
                                        </a>
                                        <c:if test="${order.status == 'PENDING' || order.status == 'CONFIRMED'}">
                                            <br>
                                            <form action="order" method="post" class="d-inline mt-2">
                                                <input type="hidden" name="action" value="cancel">
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <button type="submit" class="btn btn-outline-danger btn-sm" 
                                                        onclick="return confirm('Are you sure you want to cancel this order?')">
                                                    <i class="fas fa-times me-1"></i>Cancel
                                                </button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <!-- Empty Orders -->
                    <div class="empty-orders">
                        <i class="fas fa-receipt"></i>
                        <h3 class="text-muted">No orders found</h3>
                        <p class="text-muted mb-4">You haven't placed any orders yet. Start exploring restaurants!</p>
                        <a href="home" class="btn btn-primary btn-lg">
                            <i class="fas fa-utensils me-2"></i>Start Ordering
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

