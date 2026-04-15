<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Quản lý tồn kho</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/admin_layout.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/inventory.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
	</head>
	<body>

		<c:set var="activeMenu" value="inventory" />
		<jsp:include page="admin-sidebar.jsp" />

		<div class="main">
			<div class="page-header">
				<h2>Lịch sử xuất / nhập kho</h2>
			</div>

			<c:if test="${param.success == 'true'}">
				<div class="alert success">Giao dịch thành công! Số lượng tồn kho đã được cập nhật.</div>
			</c:if>
			<c:if test="${param.error == 'not_found'}">
				<div class="alert error" >Lỗi: Không tìm thấy sản phẩm với mã SKU đã nhập!</div>
			</c:if>
			<c:if test="${param.error == 'failed'}">
				<div class="alert error" >Lỗi: Giao dịch thất bại (Có thể do trong kho không đủ hàng để xuất).</div>
			</c:if>
			<c:if test="${param.error == 'unauthorized'}">
				<div class="alert error" >Hệ thống xảy ra lỗi. Yêu cầu đăng nhập lại</div>
			</c:if>

			<div class="top-actions">
				<button type="button" class="btn-import" onclick="toggleTransactionForm()">
					<i class="fa-solid fa-file-invoice"></i> Tạo phiếu giao dịch
				</button>
			</div>

			<div id="importFormBox" class="import-box" style="display: none;">
				<h3>Chi tiết phiếu giao dịch kho</h3>

				<form action="${pageContext.request.contextPath}/admin/inventory-action" method="post" class="import-form">
					<input type="hidden" name="action" value="transaction">

					<div class="form-grid">
						<div class="form-group full-width">
							<label>Loại giao dịch</label>
							<select name="transaction_type" required class="form-control">
								<option value="Nhập kho">Nhập kho</option>
								<option value="Xuất kho">Xuất kho</option>
							</select>
						</div>

						<div class="form-group full-width">
							<label>Nhập mã SKU sản phẩm <span style="color:red">*</span></label>
							<input type="text" name="sku" placeholder="VD: BAN-001" required class="form-control">
						</div>

						<div class="form-group">
							<label>Số lượng</label>
							<input type="number" name="quantity_changed" placeholder="VD: 50" min="1" required>
						</div>

						<div class="form-group">
							<label>Ghi chú </label>
							<input type="text" name="note" placeholder="VD: Xuất hàng trả nhà cung cấp">
						</div>
					</div>

					<div class="form-actions">
						<button type="button" class="btn-cancel" onclick="toggleTransactionForm()">Hủy bỏ</button>
						<button type="submit" class="btn-save-import">
							<i class="fa-solid fa-check"></i> Xác nhận giao dịch
						</button>
					</div>
				</form>
			</div>

			<div class="filter-bar">
				<form action="${pageContext.request.contextPath}/admin/inventory" method="get" class="filter-form">
					<div class="search-box">
						<i class="fa-solid fa-magnifying-glass"></i>
						<input type="text" name="sku" placeholder="Tìm theo mã SKU..." value="${param.sku}">
					</div>

					<select name="type">
						<option value="">Tất cả loại giao dịch</option>
						<option value="Nhập kho" <c:if test="${param.type == 'Nhập kho'}">selected</c:if>>Nhập kho</option>
						<option value="Xuất kho" <c:if test="${param.type == 'Xuất kho'}">selected</c:if>>Xuất kho</option>
						<option value="Xuất bán" <c:if test="${param.type == 'Xuất bán'}">selected</c:if>>Xuất bán</option>
						</select>

						<button type="submit" class="btn-filter">
							<i class="fa-solid fa-filter"></i> Lọc
						</button>

						<a href="${pageContext.request.contextPath}/admin/inventory" class="btn-cancel" style="text-decoration: none; display: inline-flex; align-items: center; justify-content: center;">
						Bỏ lọc
					</a>
				</form>
			</div>		

			<div class="table-container">
				<table class="inventory-table">
					<thead>
						<tr>
							<th>Mã GD</th>
							<th>Thời gian</th>
							<th>Mã SKU</th> 
							<th>Loại GD</th>
							<th>Số lượng</th>
							<th>Người thực hiện</th>
							<th>Ghi chú</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty transactionList}">
								<c:forEach var="tx" items="${transactionList}">
									<tr>
										<td class="text-muted">#INV-${tx.id}</td>

										<td class="text-muted">
											<fmt:formatDate value="${tx.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
										</td>

										<td>
											<span class="sku-badge">${tx.product.sku}</span>
										</td>

										<td>
											<span class="tx-badge <c:if test="${tx.transactionType == 'Nhập kho'}">badge-in</c:if> <c:if test="${tx.transactionType == 'Xuất bán' || tx.transactionType == 'Xuất kho'}">badge-out</c:if>">
												<i class="fa-solid <c:if test="${tx.transactionType == 'Nhập kho'}">fa-arrow-down</c:if><c:if test="${tx.transactionType == 'Xuất bán' || tx.transactionType == 'Xuất kho'}">fa-arrow-up</c:if>"></i> 
												${tx.transactionType}
											</span>
										</td>

										<td>
											<span class="qty-change <c:if test="${tx.transactionType == 'Nhập kho'}">qty-plus</c:if><c:if test="${tx.transactionType == 'Xuất bán' || tx.transactionType == 'Xuất kho'}">qty-minus</c:if>">
												<c:if test="${tx.transactionType == 'Nhập kho'}">+</c:if><c:if test="${tx.transactionType == 'Xuất bán' || tx.transactionType == 'Xuất kho'}">-</c:if>${tx.quantityChanged}
												</span>
											</td>

											<td class="text-muted">
											<c:choose>
												<c:when test="${not empty tx.user}">${tx.user.fullName}</c:when>
												<c:otherwise>Hệ thống tự động</c:otherwise>
											</c:choose>
										</td>

										<td class="text-muted note-cell">
											${tx.note}
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="7" class="empty-row">Chưa có lịch sử giao dịch nào</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>

		<script>
			function toggleTransactionForm() {
				const formBox = document.getElementById("importFormBox");

				if (formBox.style.display === "none" || formBox.style.display === "") {
					formBox.style.display = "block";
				} else {
					formBox.style.display = "none";
				}
			}
		</script>
	</body>
</html>