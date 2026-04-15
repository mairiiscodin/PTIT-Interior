<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
	<head>
		<meta charset="UTF-8">
		<title>Chi Tiết Hóa Đơn</title>
		<style>
			body {
				font-family: Arial, sans-serif;
				background-color: #f4f4f9;
				padding: 20px;
			}
			.invoice-box {
				max-width: 800px;
				margin: auto;
				padding: 30px;
				border: 1px solid #eee;
				box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
				background: #fff;
			}
			/* Container chứa tiêu đề và ngày tạo */
			.header {
				display: flex;
				justify-content: space-between;
				align-items: flex-start; /* Giúp các khối không bị lệch dòng */
				margin-bottom: 25px;
				border-bottom: 2px solid #333;
				padding-bottom: 15px;
			}

			/* Khối bên trái: Tên hóa đơn & Mã đơn */
			.header-left h2 {
				margin: 0;
				font-size: 24px;
				color: #333;
			}

			/* Khối bên phải: Ngày tạo */
			.header-right {
				text-align: right;
				min-width: 200px; /* Đảm bảo đủ khoảng trống */
			}

			.header-right p {
				margin: 0;
				font-size: 14px;
				color: #666;
			}

			/* Khối thông tin khách hàng (Dùng Grid để chia cột nếu muốn đẹp hơn) */
			.customer-info {
				display: grid;
				grid-template-columns: 1fr 1fr; /* Chia làm 2 cột */
				gap: 15px;
				margin-bottom: 20px;
				padding: 15px;
				background-color: #f9f9f9;
				border-radius: 8px;
			}
			table {
				width: 100%;
				border-collapse: collapse;
				margin-top: 20px;
			}
			th, td {
				padding: 12px;
				text-align: right;
				border-bottom: 1px solid #ddd;
			}
			th {
				background-color: #f8f9fa;
				color: #333;
			}
			th:first-child, td:first-child {
				text-align: left;
			}
			.total-row td {
				font-weight: bold;
				font-size: 1.2em;
				border-top: 2px solid #333;
			}
			.text-center {
				text-align: center;
			}

			/* --- CSS mới cho nút quay lại --- */
			.action-container {
				margin-top: 30px;
				text-align: left; /* Đặt sang trái, phải hoặc center tùy ý */
			}
			.btn-back {
				display: inline-block;
				padding: 10px 20px;
				background-color: #6c757d;
				color: #fff;
				text-decoration: none;
				border-radius: 4px;
				font-weight: bold;
				transition: background-color 0.3s;
			}
			.btn-back:hover {
				background-color: #5a6268;
			}
			/* Icon FontAwesome (nếu dự án của bạn có dùng) */
			.btn-back i {
				margin-right: 5px;
			}
		</style>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	</head>
	<body>

		<div class="invoice-box">
			<div class="header">
				<div class="header-left">
					<h2>HÓA ĐƠN MUA HÀNG</h2>
					<p style="margin-top: 5px;"><strong>Mã Đơn Hàng:</strong> #${order.id}</p>
				</div>

				<div class="header-right">
					<p><strong>Ngày tạo:</strong></p>
					<p style="font-size: 1.1em; color: #333; font-weight: bold;">
						<fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm" />
					</p>
				</div>
			</div>

			<div class="customer-info">
				<div>
					<p><i class="fa-solid fa-user"></i> <strong>Khách hàng:</strong> ${order.user.fullName}</p>
					<p><i class="fa-solid fa-credit-card"></i> <strong>Thanh toán:</strong> ${order.paymentMethod}</p>
				</div>
				<div>
					<p><i class="fa-solid fa-location-dot"></i> <strong>Địa chỉ:</strong> ${order.shippingAddress}</p>
				</div>
			</div>

			<table>
				<thead>
					<tr>
						<th>Sản Phẩm</th>
						<th>Đơn Giá</th>
						<th>Số Lượng</th>
						<th>Thành Tiền</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty orderItems}">
							<c:forEach var="item" items="${orderItems}">
								<tr>
									<td>${item.product.name}</td>
									<td>
										<fmt:formatNumber value="${item.price}" pattern="#,###.##"/> VNĐ
									</td>
									<td class="text-center">${item.quantity}</td>
									<td>
										<fmt:formatNumber value="${item.subtotal}" pattern="#,###.##"/> VNĐ
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4" class="text-center">Không có sản phẩm nào trong hóa đơn này.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
					<tr class="total-row">
						<td colspan="3">Tổng Cộng:</td>
						<td>
							<fmt:formatNumber value="${totalAmount}" pattern="#,###.##"/> VNĐ
						</td>
					</tr>
				</tfoot>
			</table>

			<div class="action-container">
				<a href="${pageContext.request.contextPath}/admin/orders" class="btn-back">
					<i class="fa-solid fa-arrow-left"></i> Quay lại
				</a>
			</div>
		</div>

	</body>
</html>