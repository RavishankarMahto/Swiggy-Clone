<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Profile - Food Delivery</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<style>
.profile-header {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: white;
	padding: 3rem 0;
}

.profile-card {
	border: none;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	border-radius: 15px;
	overflow: hidden;
}

.profile-avatar {
	width: 150px;
	height: 150px;
	border-radius: 50%;
	background: linear-gradient(45deg, #ff6b6b, #ee5a24);
	display: flex;
	align-items: center;
	justify-content: center;
	color: white;
	font-size: 4rem;
	margin: 0 auto;
}

.form-control {
	border-radius: 10px;
	border: 2px solid #e9ecef;
	padding: 12px 15px;
}

.form-control:focus {
	border-color: #667eea;
	box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
}

.btn-update {
	background: linear-gradient(45deg, #667eea, #764ba2);
	border: none;
	border-radius: 10px;
	padding: 12px 30px;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: 1px;
}

.btn-update:hover {
	transform: translateY(-2px);
	box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
}

.nav-pills .nav-link {
	border-radius: 10px;
	margin-right: 10px;
}

.nav-pills .nav-link.active {
	background: linear-gradient(45deg, #667eea, #764ba2);
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
					<li class="nav-item"><a class="nav-link" href="cart"> <i
							class="fas fa-shopping-cart me-1"></i>Cart
					</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-bs-toggle="dropdown"> <i
							class="fas fa-user me-1"></i>${sessionScope.user.username}
					</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item active" href="profile">My
									Profile</a></li>
							<li><a class="dropdown-item" href="order">My Orders</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="logout">Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Profile Header -->
	<section class="profile-header">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-8">
					<h1 class="display-5 fw-bold mb-3">
						<i class="fas fa-user me-3"></i>My Profile
					</h1>
					<p class="lead mb-0">Manage your account information and
						preferences</p>
				</div>
				<div class="col-lg-4 text-end">
					<div class="profile-avatar">
						<i class="fas fa-user"></i>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Profile Content -->
	<section class="py-5">
		<div class="container">
			<!-- Success/Error Messages -->
			<c:if test="${success != null}">
				<div class="alert alert-success alert-dismissible fade show"
					role="alert">
					<i class="fas fa-check-circle me-2"></i>${success}
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			</c:if>

			<c:if test="${error != null}">
				<div class="alert alert-danger alert-dismissible fade show"
					role="alert">
					<i class="fas fa-exclamation-triangle me-2"></i>${error}
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			</c:if>

			<c:if test="${passwordSuccess != null}">
				<div class="alert alert-success alert-dismissible fade show"
					role="alert">
					<i class="fas fa-check-circle me-2"></i>${passwordSuccess}
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			</c:if>

			<c:if test="${passwordError != null}">
				<div class="alert alert-danger alert-dismissible fade show"
					role="alert">
					<i class="fas fa-exclamation-triangle me-2"></i>${passwordError}
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			</c:if>

			<div class="row">
				<div class="col-lg-3">
					<div class="card profile-card">
						<div class="card-body text-center">
							<div class="profile-avatar mb-3">
								<i class="fas fa-user"></i>
							</div>
							<h5 class="card-title">${user.username}</h5>
							<p class="card-text text-muted">${user.role}</p>
							<hr>
							<div class="nav flex-column nav-pills" id="v-pills-tab"
								role="tablist">
								<button class="nav-link active" id="v-pills-profile-tab"
									data-bs-toggle="pill" data-bs-target="#v-pills-profile"
									type="button" role="tab">
									<i class="fas fa-user-edit me-2"></i>Profile Info
								</button>
								<button class="nav-link" id="v-pills-password-tab"
									data-bs-toggle="pill" data-bs-target="#v-pills-password"
									type="button" role="tab">
									<i class="fas fa-lock me-2"></i>Change Password
								</button>
								<button class="nav-link" id="v-pills-orders-tab"
									data-bs-toggle="pill" data-bs-target="#v-pills-orders"
									type="button" role="tab">
									<i class="fas fa-receipt me-2"></i>Order History
								</button>
							</div>
						</div>
					</div>
				</div>

				<div class="col-lg-9">
					<div class="card profile-card">
						<div class="card-body">
							<div class="tab-content" id="v-pills-tabContent">
								<!-- Profile Information Tab -->
								<div class="tab-pane fade show active" id="v-pills-profile"
									role="tabpanel">
									<h4 class="mb-4">Profile Information</h4>

									<form action="profile" method="post">
										<input type="hidden" name="action" value="update">

										<div class="row">
											<div class="col-md-6 mb-3">
												<label for="username" class="form-label">Username</label> <input
													type="text" class="form-control" id="username"
													value="${user.username}" readonly>
												<div class="form-text">Username cannot be changed</div>
											</div>

											<div class="col-md-6 mb-3">
												<label for="email" class="form-label">Email *</label> <input
													type="email" class="form-control" id="email" name="email"
													value="${user.email}" required>
											</div>
										</div>

										<div class="row">
											<div class="col-md-6 mb-3">
												<label for="phone" class="form-label">Phone Number</label> <input
													type="tel" class="form-control" id="phone" name="phone"
													value="${user.phone}">
											</div>

											<div class="col-md-6 mb-3">
												<label for="role" class="form-label">Account Type</label> <input
													type="text" class="form-control" id="role"
													value="${user.role}" readonly>
												<div class="form-text">Account type cannot be changed</div>
											</div>
										</div>

										<div class="mb-3">
											<label for="address" class="form-label">Address</label>
											<textarea class="form-control" id="address" name="address"
												rows="3">${user.address}</textarea>
										</div>

										<button type="submit" class="btn btn-primary btn-update">
											<i class="fas fa-save me-2"></i>Update Profile
										</button>
									</form>
								</div>

								<!-- Change Password Tab -->
								<div class="tab-pane fade" id="v-pills-password" role="tabpanel">
									<h4 class="mb-4">Change Password</h4>

									<form action="profile" method="post">
										<input type="hidden" name="action" value="changePassword">

										<div class="mb-3">
											<label for="currentPassword" class="form-label">Current
												Password *</label> <input type="password" class="form-control"
												id="currentPassword" name="currentPassword" required>
										</div>

										<div class="mb-3">
											<label for="newPassword" class="form-label">New
												Password *</label> <input type="password" class="form-control"
												id="newPassword" name="newPassword" required>
										</div>

										<div class="mb-3">
											<label for="confirmPassword" class="form-label">Confirm
												New Password *</label> <input type="password" class="form-control"
												id="confirmPassword" name="confirmPassword" required>
										</div>

										<button type="submit" class="btn btn-warning btn-update">
											<i class="fas fa-key me-2"></i>Change Password
										</button>
									</form>
								</div>

								<!-- Order History Tab -->
								<div class="tab-pane fade" id="v-pills-orders" role="tabpanel">
									<h4 class="mb-4">Recent Orders</h4>

									<div class="text-center py-5">
										<i class="fas fa-receipt fa-3x text-muted mb-3"></i>
										<h5 class="text-muted">Order history will appear here</h5>
										<p class="text-muted">Your recent orders and their status
											will be displayed in this section.</p>
										<a href="order" class="btn btn-primary"> <i
											class="fas fa-receipt me-2"></i>View All Orders
										</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
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
	<script>
		// Password confirmation validation
		document
				.getElementById('confirmPassword')
				.addEventListener(
						'input',
						function() {
							const newPassword = document
									.getElementById('newPassword').value;
							const confirmPassword = this.value;

							if (newPassword !== confirmPassword) {
								this
										.setCustomValidity('Passwords do not match');
							} else {
								this.setCustomValidity('');
							}
						});

		document.getElementById('newPassword').addEventListener(
				'input',
				function() {
					const confirmPassword = document
							.getElementById('confirmPassword');
					if (confirmPassword.value) {
						confirmPassword.dispatchEvent(new Event('input'));
					}
				});
	</script>
</body>
</html>


