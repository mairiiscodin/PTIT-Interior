<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
	<head>
		<title>Admin Dashboard</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/admin_layout.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/dashboard.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
	</head>

	<body>
		<c:set var="activeMenu" value="dashboard" />
		<jsp:include page="admin-sidebar.jsp" />

		<div class="main">
			<h2>THỐNG KÊ CHI TIẾT</h2>

			<div class="cards-container">
				<div class="stat-card">
					<i class="fa-solid fa-money-bill-wave" style="color: #2ecc71;"></i>
					<h3><fmt:formatNumber value="${totalRevenue}" pattern="#,###"/></h3>
					<p>Doanh thu</p>
				</div>

				<div class="stat-card">
					<i class="fa-solid fa-box" style="color: #3498db;"></i>
					<h3>${totalProducts}</h3>
					<p>Sản phẩm</p>
				</div>

				<div class="stat-card">
					<i class="fa-solid fa-file-lines" style="color: #9b59b6;"></i>
					<h3>${totalOrders}</h3>
					<p>Tổng đơn hàng</p>
				</div>

				<div class="stat-card" style="border-top: 3px solid #e67e22;">
					<i class="fa-solid fa-clock" style="color: #e67e22;"></i>
					<h3>${pendingOrders}</h3>
					<p>Chờ xác nhận</p>
				</div>

				<div class="stat-card">
					<i class="fa-solid fa-user" style="color: #1abc9c;"></i>
					<h3>${totalUsers}</h3>
					<p>Người dùng</p>
				</div>

				<div class="stat-card">
					<i class="fa-solid fa-warehouse" style="color: #7f8c8d;"></i>
					<h3>${totalStock}</h3>
					<p>Tồn kho</p>
				</div>
			</div>

			<div class="tables-grid">
				<div class="table-box">
					<h4><i class="fa-solid fa-chart-line text-danger me-2"></i> Bán chạy</h4>
					<table>
						<tbody>
							<c:forEach var="p" items="${topProducts}">
								<tr>
									<td>${p.name}</td>
									<td class="text-end"><span class="badge-sold">${p.stock}</span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="table-box">
					<h4><i class="fa-solid fa-medal text-warning me-2"></i> Khách hàng VIP</h4>
					<table>
						<tbody>
							<c:forEach var="u" items="${topUsers}">
								<tr>
									<td>${u.fullName}</td>
									<td class="text-end fw-bold" style="color: #27ae60;">
										<fmt:formatNumber value="${u.address}" pattern="#,###"/> đ
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>