<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Food Delivery - Welcome</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<style>
body {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	min-height: 100vh;
	display: flex;
	align-items: center;
}

.welcome-container {
	background: white;
	border-radius: 15px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
	overflow: hidden;
	text-align: center;
	padding: 3rem;
}

.welcome-icon {
	font-size: 4rem;
	color: #667eea;
	margin-bottom: 1rem;
}

.btn-welcome {
	background: linear-gradient(45deg, #667eea, #764ba2);
	border: none;
	border-radius: 10px;
	padding: 12px 30px;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: 1px;
	color: white;
	text-decoration: none;
	display: inline-block;
	margin: 10px;
	transition: all 0.3s ease;
}

.btn-welcome:hover {
	transform: translateY(-2px);
	box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
	color: white;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6">
				<div class="welcome-container">
					<div class="welcome-icon">
						<i class="fas fa-utensils"></i>
					</div>

					<h1 class="display-4 fw-bold mb-4">Food Delivery App</h1>
					<p class="lead mb-4">Welcome to the Food Delivery Application!</p>

					<div class="mb-4">
						<a href="welcome.jsp" class="btn-welcome"> <i
							class="fas fa-home me-2"></i>Go to Welcome Page
						</a>
					</div>

					<div class="mb-4">
						<a href="home" class="btn-welcome"> <i
							class="fas fa-utensils me-2"></i>Browse Restaurants
						</a>
					</div>

					<div class="row">
						<div class="col-md-6">
							<a href="login" class="btn btn-outline-primary w-100 mb-2"> <i
								class="fas fa-sign-in-alt me-2"></i>Login
							</a>
						</div>
						<div class="col-md-6">
							<a href="register" class="btn btn-outline-success w-100 mb-2">
								<i class="fas fa-user-plus me-2"></i>Register
							</a>
						</div>
					</div>

					<hr class="my-4">

					<div class="row text-center">
						<div class="col-md-4">
							<i class="fas fa-bolt fa-2x text-warning mb-2"></i>
							<h6>Fast Delivery</h6>
							<small class="text-muted">30 min or less</small>
						</div>
						<div class="col-md-4">
							<i class="fas fa-leaf fa-2x text-success mb-2"></i>
							<h6>Fresh Food</h6>
							<small class="text-muted">Quality ingredients</small>
						</div>
						<div class="col-md-4">
							<i class="fas fa-mobile-alt fa-2x text-primary mb-2"></i>
							<h6>Easy Ordering</h6>
							<small class="text-muted">Simple interface</small>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>