<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="sidebar">
	<div class="sidebar-header">
		<h2>Hệ thống quản trị</h2>
	</div>

	<div class="admin-profile">
		<div class="admin-info">
			<span>Xin chào</span>
			<strong>${userName}</strong>
		</div>
	</div>

	<nav class="menu">
		<a href="${pageContext.request.contextPath}/admin/dashboard" class="${activeMenu == 'dashboard' ? 'active' : ''}">
			<i class="fa-solid fa-chart-line"></i>
			<span>Tổng quan</span>
		</a>

		<a href="${pageContext.request.contextPath}/admin/products" class="${activeMenu == 'products' ? 'active' : ''}">
			<i class="fa-solid fa-couch"></i>
			<span>Sản phẩm</span>
		</a>

		<a href="${pageContext.request.contextPath}/admin/orders" class="${activeMenu == 'orders' ? 'active' : ''}">
			<i class="fa-solid fa-cart-shopping"></i>
			<span>Đơn hàng</span>
		</a>

		<a href="${pageContext.request.contextPath}/admin/users" class="${activeMenu == 'users' ? 'active' : ''}">
			<i class="fa-solid fa-users"></i>
			<span>Người dùng</span>
		</a>

		<a href="${pageContext.request.contextPath}/admin/inventory" class="${activeMenu == 'inventory' ? 'active' : ''}">
			<i class="fa-solid fa-boxes-stacked"></i> 
			<span>Tồn kho</span>
		</a>
	</nav>

	<div class="sidebar-footer">
		<a href="${pageContext.request.contextPath}/LogoutController" class="logout-btn" rel="noreferrer">
			<i class="fa-solid fa-right-from-bracket"></i>
			<span>Đăng xuất</span>
		</a>
	</div>
</div>