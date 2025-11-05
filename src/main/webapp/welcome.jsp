<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome - Food Delivery</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<style>
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.hero-section {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: white;
	padding: 100px 0;
	position: relative;
	overflow: hidden;
}

.hero-section::before {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background:
		url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grain" width="100" height="100" patternUnits="userSpaceOnUse"><circle cx="50" cy="50" r="1" fill="white" opacity="0.1"/></pattern></defs><rect width="100" height="100" fill="url(%23grain)"/></svg>');
	opacity: 0.3;
}

.hero-content {
	position: relative;
	z-index: 2;
}

.floating-animation {
	animation: float 3s ease-in-out infinite;
}

@
keyframes float { 0%, 100% {
	transform: translateY(0px);
}

50


%
{
transform


:


translateY
(


-20px


)
;


}
}
.feature-card {
	transition: transform 0.3s ease, box-shadow 0.3s ease;
	border: none;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	height: 100%;
}

.feature-card:hover {
	transform: translateY(-10px);
	box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.feature-icon {
	width: 80px;
	height: 80px;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 0 auto 20px;
	font-size: 2rem;
	color: white;
}

.feature-icon.fast {
	background: linear-gradient(45deg, #ff6b6b, #ee5a24);
}

.feature-icon.fresh {
	background: linear-gradient(45deg, #00b894, #00cec9);
}

.feature-icon.easy {
	background: linear-gradient(45deg, #6c5ce7, #a29bfe);
}

.stats-section {
	background: #f8f9fa;
	padding: 80px 0;
}

.stat-item {
	text-align: center;
	padding: 20px;
}

.stat-number {
	font-size: 3rem;
	font-weight: bold;
	color: #667eea;
	display: block;
}

.stat-label {
	color: #6c757d;
	font-size: 1.1rem;
	margin-top: 10px;
}

.cta-section {
	background: linear-gradient(45deg, #ff6b6b, #ee5a24);
	color: white;
	padding: 80px 0;
}

.btn-welcome {
	background: rgba(255, 255, 255, 0.2);
	border: 2px solid white;
	color: white;
	padding: 15px 40px;
	border-radius: 50px;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: 1px;
	transition: all 0.3s ease;
	text-decoration: none;
	display: inline-block;
}

.btn-welcome:hover {
	background: white;
	color: #ff6b6b;
	transform: translateY(-3px);
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

.testimonial-card {
	background: white;
	border-radius: 15px;
	padding: 30px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
	margin: 20px 0;
}

.testimonial-avatar {
	width: 60px;
	height: 60px;
	border-radius: 50%;
	background: linear-gradient(45deg, #667eea, #764ba2);
	display: flex;
	align-items: center;
	justify-content: center;
	color: white;
	font-size: 1.5rem;
	margin: 0 auto 20px;
}

.footer-section {
	background: #2c3e50;
	color: white;
	padding: 50px 0 30px;
}

.social-icons a {
	color: white;
	font-size: 1.5rem;
	margin: 0 15px;
	transition: color 0.3s ease;
}

.social-icons a:hover {
	color: #667eea;
}
</style>
</head>
<body>
	<!-- Hero Section -->
	<section class="hero-section">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-6 hero-content">
					<h1 class="display-4 fw-bold mb-4">Welcome to Food Delivery</h1>
					<p class="lead mb-4">Discover amazing restaurants, order your
						favorite dishes, and enjoy delicious meals delivered right to your
						doorstep. Fast, fresh, and convenient - that's our promise to you.
					</p>
					<div class="mb-4">
						<a href="home" class="btn-welcome me-3"> <i
							class="fas fa-utensils me-2"></i>Start Ordering
						</a>
						<c:choose>
							<c:when test="${sessionScope.user == null}">
								<a href="login" class="btn-welcome"> <i
									class="fas fa-sign-in-alt me-2"></i>Sign In
								</a>
							</c:when>
							<c:otherwise>
								<a href="profile" class="btn-welcome"> <i
									class="fas fa-user me-2"></i>My Profile
								</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col-lg-6 text-center">
					<div class="floating-animation">
						<i class="fas fa-hamburger"
							style="font-size: 200px; opacity: 0.3;"></i>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Features Section -->
	<section class="py-5">
		<div class="container">
			<div class="row text-center mb-5">
				<div class="col-12">
					<h2 class="display-5 fw-bold mb-3">Why Choose Our Food
						Delivery?</h2>
					<p class="lead text-muted">We make ordering food simple, fast,
						and enjoyable</p>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-4 col-md-6 mb-4">
					<div class="card feature-card">
						<div class="card-body text-center p-4">
							<div class="feature-icon fast">
								<i class="fas fa-bolt"></i>
							</div>
							<h4 class="card-title">Fast Delivery</h4>
							<p class="card-text text-muted">Get your food delivered in 30
								minutes or less. We partner with the fastest delivery drivers in
								town.</p>
						</div>
					</div>
				</div>

				<div class="col-lg-4 col-md-6 mb-4">
					<div class="card feature-card">
						<div class="card-body text-center p-4">
							<div class="feature-icon fresh">
								<i class="fas fa-leaf"></i>
							</div>
							<h4 class="card-title">Fresh Ingredients</h4>
							<p class="card-text text-muted">Only the freshest ingredients
								from trusted suppliers. Quality is our top priority.</p>
						</div>
					</div>
				</div>

				<div class="col-lg-4 col-md-6 mb-4">
					<div class="card feature-card">
						<div class="card-body text-center p-4">
							<div class="feature-icon easy">
								<i class="fas fa-mobile-alt"></i>
							</div>
							<h4 class="card-title">Easy Ordering</h4>
							<p class="card-text text-muted">Simple and intuitive
								interface. Order your favorite meals in just a few clicks.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Stats Section -->
	<section class="stats-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-3 col-md-6 mb-4">
					<div class="stat-item">
						<span class="stat-number">500+</span>
						<div class="stat-label">Restaurants</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 mb-4">
					<div class="stat-item">
						<span class="stat-number">50K+</span>
						<div class="stat-label">Happy Customers</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 mb-4">
					<div class="stat-item">
						<span class="stat-number">1M+</span>
						<div class="stat-label">Orders Delivered</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-6 mb-4">
					<div class="stat-item">
						<span class="stat-number">4.8★</span>
						<div class="stat-label">Average Rating</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Testimonials Section -->
	<section class="py-5">
		<div class="container">
			<div class="row text-center mb-5">
				<div class="col-12">
					<h2 class="display-5 fw-bold mb-3">What Our Customers Say</h2>
					<p class="lead text-muted">Real reviews from real customers</p>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-4 col-md-6 mb-4">
					<div class="testimonial-card">
						<div class="testimonial-avatar">
							<i class="fas fa-user"></i>
						</div>
						<h5>Sarah Johnson</h5>
						<p class="text-muted mb-3">"Amazing service! My food arrived
							hot and fresh. The delivery was super fast and the driver was
							very polite."</p>
						<div class="text-warning">
							<i class="fas fa-star"></i> <i class="fas fa-star"></i> <i
								class="fas fa-star"></i> <i class="fas fa-star"></i> <i
								class="fas fa-star"></i>
						</div>
					</div>
				</div>

				<div class="col-lg-4 col-md-6 mb-4">
					<div class="testimonial-card">
						<div class="testimonial-avatar">
							<i class="fas fa-user"></i>
						</div>
						<h5>Mike Chen</h5>
						<p class="text-muted mb-3">"Great variety of restaurants and
							cuisines. The app is easy to use and the food quality is
							consistently excellent."</p>
						<div class="text-warning">
							<i class="fas fa-star"></i> <i class="fas fa-star"></i> <i
								class="fas fa-star"></i> <i class="fas fa-star"></i> <i
								class="fas fa-star"></i>
						</div>
					</div>
				</div>

				<div class="col-lg-4 col-md-6 mb-4">
					<div class="testimonial-card">
						<div class="testimonial-avatar">
							<i class="fas fa-user"></i>
						</div>
						<h5>Emily Davis</h5>
						<p class="text-muted mb-3">"I love how I can track my order in
							real-time. The customer service is fantastic and always helpful."</p>
						<div class="text-warning">
							<i class="fas fa-star"></i> <i class="fas fa-star"></i> <i
								class="fas fa-star"></i> <i class="fas fa-star"></i> <i
								class="fas fa-star"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- CTA Section -->
	<section class="cta-section">
		<div class="container">
			<div class="row text-center">
				<div class="col-lg-8 mx-auto">
					<h2 class="display-5 fw-bold mb-4">Ready to Order?</h2>
					<p class="lead mb-4">Join thousands of satisfied customers and
						experience the best food delivery service in town.</p>
					<div class="mb-4">
						<a href="home" class="btn-welcome me-3"> <i
							class="fas fa-utensils me-2"></i>Browse Restaurants
						</a>
						<c:if test="${sessionScope.user == null}">
							<a href="register" class="btn-welcome"> <i
								class="fas fa-user-plus me-2"></i>Sign Up Now
							</a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer class="footer-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-4 mb-4">
					<h5 class="mb-3">
						<i class="fas fa-utensils me-2"></i>Food Delivery
					</h5>
					<p class="text-muted">Delivering happiness, one meal at a time.
						We connect you with the best restaurants in your area.</p>
					<div class="social-icons">
						<a href="#"><i class="fab fa-facebook"></i></a> <a href="#"><i
							class="fab fa-twitter"></i></a> <a href="#"><i
							class="fab fa-instagram"></i></a> <a href="#"><i
							class="fab fa-linkedin"></i></a>
					</div>
				</div>

				<div class="col-lg-2 col-md-6 mb-4">
					<h6 class="mb-3">Quick Links</h6>
					<ul class="list-unstyled">
						<li><a href="home" class="text-muted text-decoration-none">Home</a></li>
						<li><a href="search" class="text-muted text-decoration-none">Search</a></li>
						<li><a href="about" class="text-muted text-decoration-none">About</a></li>
						<li><a href="contact" class="text-muted text-decoration-none">Contact</a></li>
					</ul>
				</div>

				<div class="col-lg-2 col-md-6 mb-4">
					<h6 class="mb-3">Support</h6>
					<ul class="list-unstyled">
						<li><a href="help" class="text-muted text-decoration-none">Help
								Center</a></li>
						<li><a href="faq" class="text-muted text-decoration-none">FAQ</a></li>
						<li><a href="terms" class="text-muted text-decoration-none">Terms</a></li>
						<li><a href="privacy" class="text-muted text-decoration-none">Privacy</a></li>
					</ul>
				</div>

				<div class="col-lg-4 mb-4">
					<h6 class="mb-3">Contact Info</h6>
					<div class="text-muted">
						<p>
							<i class="fas fa-map-marker-alt me-2"></i>123 Food Street, City,
							State 12345
						</p>
						<p>
							<i class="fas fa-phone me-2"></i>+1 (555) 123-4567
						</p>
						<p>
							<i class="fas fa-envelope me-2"></i>info@fooddelivery.com
						</p>
					</div>
				</div>
			</div>

			<hr class="my-4">

			<div class="row align-items-center">
				<div class="col-md-6">
					<p class="mb-0 text-muted">&copy; 2024 Food Delivery App. All
						rights reserved.</p>
				</div>
				<div class="col-md-6 text-end">
					<p class="mb-0 text-muted">
						Made with <i class="fas fa-heart text-danger"></i> for food lovers
					</p>
				</div>
			</div>
		</div>
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>