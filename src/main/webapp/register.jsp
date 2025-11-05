<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Register - Food Delivery</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<style>
body {
	background: linear-gradient(135deg, #667eea 0%, #764ba strains 100%);
	min-height: 100vh;
	padding: 2rem 0;
}

.register-container {
	background: white;
	border-radius: 15px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
	overflow: hidden;
}

.register-header {
	background: linear-gradient(45deg, #ff6b6b, #ee5a24);
	color: white;
	padding: 2rem;
	text-align: center;
}

.register-form {
	padding: 2rem;
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

.btn-register {
	background: linear-gradient(45deg, #667eea, #764ba2);
	border: none;
	border-radius: 10px;
	padding: 12px;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: 1px;
}

.btn-register:hover {
	transform: translateY(-2px);
	box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
}

.password-strength {
	font-size: 0.8em;
	margin-top: 5px;
}

.strength-weak {
	color: #dc3545;
}

.strength-medium {
	color: #ffc107;
}

.strength-strong {
	color: #28a745;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6">
				<div class="register-container">
					<div class="register-header">
						<i class="fas fa-user-plus fa-3x mb-3"></i>
						<h3 class="mb-0">Join Food Delivery</h3>
						<p class="mb-0">Create your account and start ordering
							delicious food</p>
					</div>

					<div class="register-form">
						<c:if test="${error != null}">
							<div class="alert alert-danger alert-dismissible fade show"
								role="alert">
								<i class="fas fa-exclamation-triangle me-2"></i>${error}
								<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
							</div>
						</c:if>

						<form action="register" method="post" id="registerForm">
							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="username" class="form-label"> <i
										class="fas fa-user me-2"></i>Username *
									</label> <input type="text" class="form-control" id="username"
										name="username" value="${username}"
										placeholder="Choose a username" required>
								</div>

								<div class="col-md-6 mb-3">
									<label for="email" class="form-label"> <i
										class="fas fa-envelope me-2"></i>Email *
									</label> <input type="email" class="form-control" id="email"
										name="email" value="${email}" placeholder="Enter your email"
										required>
								</div>
							</div>

							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="password" class="form-label"> <i
										class="fas fa-lock me-2"></i>Password *
									</label> <input type="password" class="form-control" id="password"
										name="password" placeholder="Create a password" required
										onkeyup="checkPasswordStrength()">
									<div id="passwordStrength" class="password-strength"></div>
								</div>

								<div class="col-md-6 mb-3">
									<label for="confirmPassword" class="form-label"> <i
										class="fas fa-lock me-2"></i>Confirm Password *
									</label> <input type="password" class="form-control"
										id="confirmPassword" name="confirmPassword"
										placeholder="Confirm your password" required
										onkeyup="checkPasswordMatch()">
									<div id="passwordMatch" class="password-strength"></div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="phone" class="form-label"> <i
										class="fas fa-phone me-2"></i>Phone Number
									</label> <input type="tel" class="form-control" id="phone" name="phone"
										value="${phone}" placeholder="Enter your phone number">
								</div>

								<div class="col-md-6 mb-3">
									<label for="role" class="form-label"> <i
										class="fas fa-user-tag me-2"></i>Account Type *
									</label> <select class="form-control" id="role" name="role" required>
										<option value="">Select account type</option>
										<option value="CUSTOMER"
											${selectedRole == 'CUSTOMER' ? 'selected' : ''}>Customer</option>
										<option value="RESTAURANT_OWNER"
											${selectedRole == 'RESTAURANT_OWNER' ? 'selected' : ''}>Restaurant
											Owner</option>
									</select>
								</div>
							</div>

							<div class="mb-3">
								<label for="address" class="form-label"> <i
									class="fas fa-map-marker-alt me-2"></i>Address
								</label>
								<textarea class="form-control" id="address" name="address"
									rows="3" placeholder="Enter your address">${address}</textarea>
							</div>

							<div class="mb-3 form-check">
								<input type="checkbox" class="form-check-input" id="terms"
									required> <label class="form-check-label" for="terms">
									I agree to the <a href="#" class="text-decoration-none">Terms
										and Conditions</a> and <a href="#" class="text-decoration-none">Privacy
										Policy</a>
								</label>
							</div>

							<button type="submit"
								class="btn btn-primary btn-register w-100 mb-3">
								<i class="fas fa-user-plus me-2"></i>Create Account
							</button>
						</form>

						<div class="text-center">
							<p class="mb-0">
								Already have an account? <a href="login"
									class="text-decoration-none fw-bold">Sign in here</a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		function checkPasswordStrength() {
			const password = document.getElementById('password').value;
			const strengthDiv = document.getElementById('passwordStrength');

			if (password.length === 0) {
				strengthDiv.innerHTML = '';
				return;
			}

			let strength = 0;
			let message = '';

			if (password.length >= 6)
				strength++;
			if (password.match(/[a-z]/))
				strength++;
			if (password.match(/[A-Z]/))
				strength++;
			if (password.match(/[0-9]/))
				strength++;
			if (password.match(/[^a-zA-Z0-9]/))
				strength++;

			switch (strength) {
			case 0:
			case 1:
				message = '<span class="strength-weak">Very Weak</span>';
				break;
			case 2:
				message = '<span class="strength-weak">Weak</span>';
				break;
			case 3:
				message = '<span class="strength-medium">Medium</span>';
				break;
			case 4:
				message = '<span class="strength-strong">Strong</span>';
				break;
			case 5:
				message = '<span class="strength-strong">Very Strong</span>';
				break;
			}

			strengthDiv.innerHTML = message;
		}

		function checkPasswordMatch() {
			const password = document.getElementById('password').value;
			const confirmPassword = document.getElementById('confirmPassword').value;
			const matchDiv = document.getElementById('passwordMatch');

			if (confirmPassword.length === 0) {
				matchDiv.innerHTML = '';
				return;
			}

			if (password === confirmPassword) {
				matchDiv.innerHTML = '<span class="strength-strong">Passwords match</span>';
			} else {
				matchDiv.innerHTML = '<span class="strength-weak">Passwords do not match</span>';
			}
		}

		// Form validation
		document.getElementById('registerForm').addEventListener(
				'submit',
				function(e) {
					const password = document.getElementById('password').value;
					const confirmPassword = document
							.getElementById('confirmPassword').value;

					if (password !== confirmPassword) {
						e.preventDefault();
						alert('Passwords do not match!');
						return false;
					}

					if (password.length < 6) {
						e.preventDefault();
						alert('Password must be at least 6 characters long!');
						return false;
					}
				});
	</script>
</body>
</html>