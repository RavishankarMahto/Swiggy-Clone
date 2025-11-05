<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Error - Food Delivery</title>
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

.error-container {
	background: white;
	border-radius: 15px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
	overflow: hidden;
	text-align: center;
	padding: 3rem;
}

.error-icon {
	font-size: 5rem;
	color: #dc3545;
	margin-bottom: 1rem;
}

.error-code {
	font-size: 6rem;
	font-weight: bold;
	color: #667eea;
	margin-bottom: 1rem;
}

.btn-home {
	background: linear-gradient(45deg, #667eea, #764ba2);
	border: none;
	border-radius: 10px;
	padding: 12px 30px;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: 1px;
	color: white;
}

.btn-home:hover {
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
				<div class="error-container">
					<div class="error-icon">
						<i class="fas fa-exclamation-triangle"></i>
					</div>

					<div class="error-code">Oops!</div>

					<h2 class="mb-4">Something went wrong</h2>

					<div class="mb-4">
						<c:choose>
							<c:when test="${error != null}">
								<p class="text-muted">${error}</p>
							</c:when>
							<c:otherwise>
								<p class="text-muted">We encountered an unexpected error.
									Please try again later.</p>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="row">
						<div class="col-md-6 mb-3">
							<a href="home" class="btn btn-home w-100"> <i
								class="fas fa-home me-2"></i>Go Home
							</a>
						</div>
						<div class="col-md-6 mb-3">
							<button onclick="history.back()"
								class="btn btn-outline-primary w-100">
								<i class="fas fa-arrow-left me-2"></i>Go Back
							</button>
						</div>
					</div>

					<hr class="my-4">

					<p class="text-muted small">If this problem persists, please
						contact our support team.</p>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


