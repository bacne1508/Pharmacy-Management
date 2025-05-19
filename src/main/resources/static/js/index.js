$(document).ready(function() {
	$("#login-btn").click(function(event) {
		event.preventDefault(); 
		const formData = getLoginForm();
		if (!validateLoginForm(formData)) return;

		postRequest('/api/auth/login', formData, handleLoginSuccess, handleLoginError);
	});

	function getLoginForm() {
		return {
			username: $('#index-name').val(),
			password: $('#index-password').val()
		};
	}

	function validateLoginForm(data) {
		let isValid = true;
		if (!data.username) {
			isValid = false;
			showError('#index-name', '#index-name-error');
		}
		if (!data.password) {
			isValid = false;
			showError('#index-password', '#index-password-error');
		}
		return isValid;
	}

	function showError(inputSelector, errorSelector) {
		$(inputSelector).parent('.input-group').addClass('has-error');
		$(errorSelector).css("visibility", "visible");
	}

	function handleLoginSuccess(res) {
		if (res.success && res.content) {
			const user = res.content.user;
			sessionStorage.setItem('id', user.id || '');
			const role = user.auth || extractRole(user.authorities);
			sessionStorage.setItem('auth', role);
			sessionStorage.setItem('username', user.username || '');

			window.location.href = redirectToDashboard(role);
		} else {
			alert(res.message || "Login failed. Please check your credentials.");
		}
	}

	function extractRole(authorities) {
		if (authorities && authorities.length > 0) {
			const rawRole = authorities[0].authority;
			return rawRole.startsWith('ROLE_') ? rawRole.substring(5) : rawRole;
		}
		return null;
	}

	function redirectToDashboard(roleNumber) {
		const AUTH_PATIENT = 0;
		const AUTH_ADMIN = 1;
		const AUTH_DOCTOR = 3;
		const routes = {
			[AUTH_PATIENT]: "/user/home",
			[AUTH_ADMIN]: "/admin/dashboard",
			[AUTH_DOCTOR]: "/doctor/dashboard"
		};
		const defaultRoute = "/"; // Trang mặc định nếu vai trò không khớp

    	if (routes.hasOwnProperty(roleNumber)) {
	        return routes[roleNumber];
	    } else {
	        return defaultRoute;
	    }
	}

	function handleLoginError(jqXHR, textStatus, errorMessage) {
		console.error("Login AJAX error:", jqXHR.status, textStatus, errorMessage);
		alert(errorMessage || "An error occurred during login. Please try again.");
	}
});

document.addEventListener('DOMContentLoaded', function() {
	const passwordInput = document.getElementById('password');
	const togglePasswordIcon = document.getElementById('togglePasswordIcon');

	if (passwordInput && togglePasswordIcon) {
		togglePasswordIcon.addEventListener('click', function() {
			const isPassword = passwordInput.type === 'password';
			passwordInput.type = isPassword ? 'text' : 'password';
			this.classList.toggle('fa-eye', isPassword);
			this.classList.toggle('fa-eye-slash', !isPassword);
		});
	} else {
		if (!passwordInput) console.error("Password input field not found!");
		if (!togglePasswordIcon) console.error("Toggle password icon not found!");
	}
});
