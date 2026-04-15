<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Quản lý đơn hàng</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/admin_layout.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/orders.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
	</head>
	<body>

		<c:set var="activeMenu" value="orders" />
		<jsp:include page="admin-sidebar.jsp" />

		<!-- Main -->
		<div class="main">

			<div class="page-header">
				<h2>Quản lý đơn hàng</h2>
			</div>

			<!-- Bộ lọc -->
			<div class="filter-bar">
				<form action="${pageContext.request.contextPath}/admin/orders" method="get" class="filter-form">
					<div class="search-box">
						<i class="fa-solid fa-magnifying-glass"></i>
						<input type="text" name="keyword" placeholder="Tìm mã đơn, tên khách..." value="${param.keyword}">
					</div>

					<select name="status">
						<option value="">Tất cả trạng thái</option>

						<c:forEach items="${statusList}" var="stt">
							<option value="${stt}" ${status == stt ? 'selected' : ''}>
								${stt}
							</option>
						</c:forEach>
					</select>

					<button type="submit" class="btn-filter">
						<i class="fa-solid fa-filter"></i> Lọc
					</button>
				</form>
			</div>

			<!-- Bảng đơn hàng -->
			<div class="table-container">
				<table class="order-table">
					<thead>
						<tr>
							<th>Mã đơn</th>
							<th>Khách hàng</th>
							<th>SĐT</th>
							<th>Ngày đặt</th>
							<th>Tổng tiền</th>
							<th>Thanh toán</th>
							<th>Trạng thái</th>
							<th>Cập nhật</th>
							<th>Hành động</th>
						</tr>
					</thead>
					<tbody>

						<c:choose>
							<c:when test="${not empty orderList}">
								<c:forEach var="o" items="${orderList}">
									<tr>
										<td>#${o.id}</td>

										<!-- Khách hàng -->
										<td>
											<c:choose>
												<c:when test="${o.user != null}">
													${o.user.fullName}
												</c:when>
												<c:otherwise>---</c:otherwise>
											</c:choose>
										</td>

										<!-- SĐT -->
										<td>
											<c:choose>
												<c:when test="${o.user != null}">
													${o.user.phone}
												</c:when>
												<c:otherwise>---</c:otherwise>
											</c:choose>
										</td>

										<!-- Ngày đặt -->
										<td>
											<c:choose>
												<c:when test="${o.createdAt != null}">
													<fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm" />
												</c:when>
												<c:otherwise>---</c:otherwise>
											</c:choose>
										</td>

										<!-- Tổng tiền -->
										<td>
											<fmt:formatNumber value="${o.totalAmount}" pattern="#,###" /> VND
										</td>

										<!-- Thanh toán -->
										<td>
											<span class="payment-badge ${o.paymentMethod == 'COD' ? 'unpaid' : 'paid'}">
												${o.paymentMethod}
											</span>
										</td>

										<!-- Trạng thái -->
										<td>
											<span class="status-badge
												  ${o.status == 'Chờ xác nhận' ? 'pending' : ''}
												  ${o.status == 'Đã xác nhận' ? 'confirmed' : ''}
												  ${o.status == 'Đang giao' ? 'shipping' : ''}
												  ${o.status == 'Hoàn thành' ? 'completed' : ''}
												  ${o.status == 'Đã hủy' ? 'cancelled' : ''}">
												${o.status}
											</span>
										</td>

										<!-- Cập nhật trạng thái -->
										<td>
											<form action="${pageContext.request.contextPath}/admin/update-order-status" method="post" class="status-form">
												<input type="hidden" name="orderId" value="${o.id}">
												<select name="status">
													<c:forEach items="${statusList}" var="stt">
														<option value="${stt}" ${o.status == stt ? 'selected' : ''}>
															${stt}
														</option>
													</c:forEach>
												</select>
												<button type="submit" class="btn-update">Lưu</button>
											</form>
										</td>

										<!-- Xem chi tiết -->
										<td>
											<a href="${pageContext.request.contextPath}/admin/order-detail?id=${o.id}" class="btn-view">
												<i class="fa-solid fa-eye"></i> Xem
											</a>
										</td>
									</tr>
								</c:forEach>
							</c:when>

							<c:otherwise>
								<tr>
									<td colspan="9" class="empty-row">Không có đơn hàng nào</td>
								</tr>
							</c:otherwise>
						</c:choose>

					</tbody>
				</table>
			</div>
		</div>

	</body>
</html>